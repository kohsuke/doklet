package org.kohsuke.doklet;

import com.sun.javadoc.RootDoc;
import com.sun.tools.doclets.formats.html.FrameOutputWriter;
import com.sun.tools.doclets.formats.html.HtmlDoclet;
import com.sun.tools.doclets.internal.toolkit.AbstractDoclet;
import com.sun.tools.doclets.internal.toolkit.util.ClassTree;
import com.sun.tools.doclets.internal.toolkit.util.DocletAbortException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Hack to use {@link MyFrameOutputWriter} in place of standard {@link FrameOutputWriter}
 *
 * @author Kohsuke Kawaguchi
 */
public class Doklet extends HtmlDoclet {

    public static boolean start(RootDoc root) {
        Doklet doclet = new Doklet();
        return doclet._start(root);
    }

    private boolean _start(RootDoc root) {
        try {
            Field f = AbstractDoclet.class.getField("configuration");
            f.set(this,configuration());

            configuration.root = root;
            Method m = AbstractDoclet.class.getDeclaredMethod("startGeneration", RootDoc.class);
            m.setAccessible(true);
            m.invoke(this, root);
            return true;
        } catch (NoSuchMethodException e) {
            throw new Error(e);
        } catch (IllegalAccessException e) {
            throw new Error(e);
        } catch (InvocationTargetException e) {
            throw new Error(e);
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    @Override
    protected void generateOtherFiles(RootDoc root, ClassTree classtree) throws Exception {
        super.generateOtherFiles(root, classtree);

        // generate our custom index.html
        generateFrame();
    }

    private void generateFrame() {
        String filename = "";
        try {
            filename = "index.html";
            MyFrameOutputWriter framegen = new MyFrameOutputWriter(configuration, filename);
            framegen.generateFrameFile();
            framegen.close();
        } catch (IOException exc) {
            configuration.standardmessage.error(
                        "doclet.exception_encountered",
                        exc.toString(), filename);
            throw new DocletAbortException();
        }
    }
}
