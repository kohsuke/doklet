package org.kohsuke.doklet;

import com.sun.javadoc.ClassDoc;
import com.sun.tools.doclets.formats.html.ConfigurationImpl;
import com.sun.tools.doclets.formats.html.FrameOutputWriter;
import com.sun.tools.doclets.formats.html.markup.HtmlAttr;
import com.sun.tools.doclets.formats.html.markup.HtmlTag;
import com.sun.tools.doclets.formats.html.markup.HtmlTree;
import com.sun.tools.doclets.formats.html.markup.RawHtml;
import com.sun.tools.doclets.internal.toolkit.Content;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * Generate short class name to fully-qualified name mapping.
 *
 * @author Kohsuke Kawaguchi
 */
public class MyFrameOutputWriter extends FrameOutputWriter {
    public MyFrameOutputWriter(ConfigurationImpl configuration, String filename) throws IOException {
        super(configuration, filename);
    }

    @Override
    public void generateFrameFile() {
        super.generateFrameFile();
    }

    @Override
    protected Content getFramesetJavaScript() {
        ContentList lst = new ContentList();
        lst.addContent(createShortHandScript());
        lst.addContent(super.getFramesetJavaScript());
        return lst;
    }

    private HtmlTree createShortHandScript() {
        try {
            String script = IOUtils.toString(getClass().getResourceAsStream("script.js"));

            StringBuilder map = new StringBuilder();
            for (ClassDoc c : configuration.root.classes()) {
                map.append("m['").append(c.typeName()).append("']='").append(c.qualifiedName()).append("';\n");
            }
            script = script.replace("$MAPPING$",map);

            HtmlTree s = new HtmlTree(HtmlTag.SCRIPT);
            s.addAttr(HtmlAttr.TYPE, "text/javascript");
            RawHtml scriptContent = new RawHtml(script);
            s.addContent(scriptContent);
            return s;
        } catch (IOException e) {
            throw new Error(e);
        }
    }
}
