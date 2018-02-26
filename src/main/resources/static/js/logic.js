var app = app || {};

app.logic = {

    addUserRowEventListener: function() {
        $(".user-row-clickable").click(function() {
            window.location = $(this).data("href");
        });
    },

    setActiveMenu: function() {
        $('a[href="' + window.location.pathname + '"]').parents('li').addClass('active');
    }

};
