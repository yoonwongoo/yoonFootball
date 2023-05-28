var token = $("meta[name=_csrf_token]").attr("content");
var header = $("meta[name=_csrf_header]").attr("content");

$.fn.serializeObject = function() {
    var obj = null;
    try {
        if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
            var arr = this.serializeArray();
            console.log(arr);
            if (arr) {
                obj = {};
                jQuery.each(arr, function() {
                    obj[this.name] = this.value;
                });
            }
        }
    } catch (e) {
        alert(e.message);
    } finally {

    }
    return obj;
};
