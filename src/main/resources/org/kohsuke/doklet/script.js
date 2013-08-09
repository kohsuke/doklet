var m = {};

$MAPPING$

(function() {
    var s = ""+window.location.search;
    if (s != "" && s != "undefined") {
        s = s.substring(1);
        if (m[s])
            window.location.search = m[s].split('.').join('/')+".html";
    }
})();

