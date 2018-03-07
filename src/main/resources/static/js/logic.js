var app = app || {};

app.logic = {

    addUserRowEventListener: function() {
        $(".user-row-clickable").on("click", function() {
            window.location = $(this).data("href");
        });
    },

    setActiveMenu: function() {
        $('a[href="' + window.location.pathname + '"]').parents('li').addClass('active');
    },

    languageSelect: function() {

        $(".language").on("click", function() {
            var reqLang = $(this).data("lang");
            // Possible issue: query string of previous request will be completely lost
            window.location.replace(window.location.origin + window.location.pathname + "?lang=" + reqLang);
        });
    }

};
