doklet
======

This Javadoc doclet makes minor enhancements to standard doclet:

* Generate a short class name to full name mapping. Allow you to link to `index.html?ClassName` and have it redirect to `index.html?fully/qualified/ClassName.html`.

To use it from Maven, put this in `reporting/plugins` or `build/plugins` (depending on how you use maven-javadoc-plugin):

      <plugin>
        <artifactId>maven-javadoc-plugin</artifactId>
        <configuration>
          <aggregate>true</aggregate>
          <doclet>org.kohsuke.doklet.Doklet</doclet>
          <linksource>true</linksource>
          <docletArtifact>
            <groupId>org.kohsuke</groupId>
            <artifactId>doklet</artifactId>
            <version>1.0</version>
          </docletArtifact>
        </configuration>
      </plugin>
