package org.kohsuke.doklet;

import com.sun.tools.doclets.formats.html.markup.StringContent;
import com.sun.tools.doclets.internal.toolkit.Content;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kohsuke Kawaguchi
 */
class ContentList extends Content {
    List<Content> contents = new ArrayList<Content>();

    @Override
    public void addContent(Content content) {
        contents.add(content);
    }

    @Override
    public void addContent(String stringContent) {
        addContent(new StringContent(stringContent));
    }

    @Override
    public void write(StringBuilder contentBuilder) {
        for (Content content : contents) {
            content.write(contentBuilder);
        }
    }

    @Override
    public boolean isEmpty() {
        return contents.isEmpty();
    }
}
