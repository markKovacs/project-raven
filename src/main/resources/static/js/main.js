var app = app || {};

app.init = function() {
    app.logic.addUserRowEventListener();
    app.logic.setActiveMenu();
};

$(function() {
    app.init();
});
