package org.tinywebserver.translator;

import org.tinywebserver.config.TinyServletConfig;
import org.tinywebserver.scanner.Scanner;
import org.tinywebserver.scanner.Source;
import org.tinywebserver.scanner.Token;
import org.tinywebserver.scanner.jsp.JspScanner;
import org.tinywebserver.scanner.jsp.JspTokenType;
import org.tinywebserver.util.TinyWebServerUtility;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by user on 11/25/2016.
 */
public class JspToServletConverter {

    private static final String PACKAGE_NAME_TRANSLATED_SERVLET = "org.tinywebserver.servlet.translated.";

    private static final String STATIC_KEYWORD = "static ";

    private static final String IMPORT_KEYWORD = "import ";

    private static final String SEMI_COLON = ";";

    private static final String JAVA_FILE_EXTENSION = ".java";

    private static final String JSP_FILE_EXTENSION = ".jsp";

    private static final String UTF_8 = "UTF-8";

    private final String DESTINATION_DIRECTORY_CONVERTED_SERVLET = TinyServletConfig.getTinyServletConfigInstance().getProperties().getProperty("tinywebserver.jsp.destination.directory");

    private final String JSP_ROOT_DIRECTORY = TinyServletConfig.getTinyServletConfigInstance().getProperties().getProperty("tinywebserver.jsp.directory");

    private boolean updateServletConfiguration;

    private String destinationDirectory;

    private String javaFileName;

    private PrintWriter writer;

    private List<Token> tokens;

    public JspToServletConverter(boolean updateServletConfiguration) throws IOException {
        setUpdateServletConfiguration(updateServletConfiguration);
    }

    public void setUpdateServletConfiguration(boolean updateServletConfiguration) {
        this.updateServletConfiguration = updateServletConfiguration;
    }

    public boolean isUpdateServletConfiguration() {
        return updateServletConfiguration;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setJavaFileName(String javaFileName) {
        this.javaFileName = javaFileName;
    }

    public String getJavaFileName() {
        return javaFileName;
    }

    public void setDestinationDirectory(String destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    public String getDestinationDirectory() {
        return destinationDirectory;
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public void traverseJspFolderAndConvertToJsp(File curDir) throws IOException, InterruptedException, ClassNotFoundException {

        File[] filesList = curDir.listFiles();
        for (File file : filesList) {
            if (file.isDirectory())
                traverseJspFolderAndConvertToJsp(file);
            if (file.isFile()) {
                if (isUpdateServletConfiguration())
                    updateServletConfiguration(file);
                else
                    translateJspToServlet(file);
            }
        }
    }

    private void updateServletConfiguration(File file) throws ClassNotFoundException, IOException {
        setJavaFileName(TinyWebServerUtility.capitalizeFirstLetter(file.getName().replace(JSP_FILE_EXTENSION, JAVA_FILE_EXTENSION)));
        String resourceLocation = "/"+getResourceName(file).replaceAll("\\\\","/");
        TinyServletConfig tinyServletConfigInstance = TinyServletConfig.getTinyServletConfigInstance();
        String servletName = PACKAGE_NAME_TRANSLATED_SERVLET + getJavaFileName().replace(JAVA_FILE_EXTENSION, "");
        Class classInstance = Class.forName(servletName);
        tinyServletConfigInstance.getTinyServletConfigs().
                putIfAbsent(resourceLocation, classInstance);
    }

    private void createPrintWriter(File file) throws FileNotFoundException, UnsupportedEncodingException {

        setJavaFileName(TinyWebServerUtility.capitalizeFirstLetter(file.getName().replace(JSP_FILE_EXTENSION, JAVA_FILE_EXTENSION)));
        String fileNameJavaWithFullPath = getDestinationDirectory() + "/" + getJavaFileName();
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileNameJavaWithFullPath, false), UTF_8));
        PrintWriter printWriter = new PrintWriter(writer, true);
        setWriter(printWriter);
    }

    public void translateJspToServlet(File file) throws IOException, InterruptedException, ClassNotFoundException {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        String resourceLocation = getResourceName(file);

        InputStream inputStream = classloader.getResourceAsStream(resourceLocation);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF_8));

        if (destinationDirectory == null)
            setDestinationDirectory(getDestinationDirectoryServlet());

        createPrintWriter(file);

        String className = TinyWebServerUtility.capitalizeFirstLetter(file.getName().replace(JSP_FILE_EXTENSION, ""));

        constructServlet(bufferedReader, className);

    }

    private String getDestinationDirectoryServlet() {

        File currentDirectory = new File(new File(".").getAbsolutePath());
        String rootDirectory = Paths.get(".").toAbsolutePath().normalize().toString();
        return new File(rootDirectory, DESTINATION_DIRECTORY_CONVERTED_SERVLET).toString();
    }

    private String getResourceName(File file) {

        String splitRegex = Pattern.quote(System.getProperty("file.separator") + JSP_ROOT_DIRECTORY);
        String[] splitFilePath = file.toString().split(splitRegex);
        String resourceName = JSP_ROOT_DIRECTORY + splitFilePath[1];
        return resourceName;
    }

    private void addImportPackageToServlet() {

        for (Token token : getTokens()) {
            JspTokenType tokenType = (JspTokenType) token.getTokenType();
            switch (tokenType) {
                case IMPORT:
                    String[] importList = token.getValue().split(",");
                    for (String imp : importList) {
                        writer.println(IMPORT_KEYWORD + imp.trim() + SEMI_COLON);
                    }
            }
        }
    }

    private void createServlet() {

        writer.println("public class " + getJavaFileName().replace(".java", "") + " extends TinyServlet { ");
        writer.println("");
    }

    private void constructServlet(BufferedReader bufferedReader, String fileName) throws IOException, InterruptedException {

        Scanner scanner = new JspScanner(new Source(bufferedReader));

        setTokens(scanner.getTokens());

        addPredefinedPackageToServlet(fileName);

        addImportPackageToServlet();

        createServlet();

        addDeclaration();

        addDoRequestMethod();

        addScriptletHtmlExpression();
    }

    private void addScriptletHtmlExpression() {

        for (Token token : getTokens()) {
            JspTokenType tokenType = (JspTokenType) token.getTokenType();
            String tokenValue = token.getValue();
            if (tokenType == JspTokenType.HTML)
                tokenValue = token.getValue().replace("\\", "\\\\").replace("\"", "\\\"").replace("'", "\\'");
            switch (tokenType) {
                case HTML:
                    writer.println("out.println(\"" + tokenValue + "\");");
                    break;
                case EXPRESSION:
                    writer.println("out.println(" + tokenValue + ");");
                    break;
                case SCRIPTLET:
                    writer.println(tokenValue);
            }
        }
        writer.println("}}");
        writer.close();
    }

    private void addDoRequestMethod() {

        writer.println(" public void doRequest(TinyServletRequest request, TinyServletResponse response) throws IOException{ ");
        writer.println(" PrintWriter out = response.getOutputWriter();");
    }

    private void addDeclaration() {

        for (Token token : getTokens()) {
            JspTokenType tokenType = (JspTokenType) token.getTokenType();
            switch (tokenType) {
                case DECLARATION:
                    writer.println(STATIC_KEYWORD + token.getValue());
            }
        }
    }

    private void addPredefinedPackageToServlet(String fileName) {
        writer.println("package org.tinywebserver.servlet.translated; ");
        writer.println("import java.io.*;");
        writer.println("import java.util.*;");
        writer.println("import org.tinywebserver.servlet.*;");
        writer.println("import org.tinywebserver.session.*;");
    }
}
