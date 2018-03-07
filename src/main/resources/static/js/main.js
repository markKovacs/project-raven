var app = app || {};

app.init = function() {
    app.logic.addUserRowEventListener();
    app.logic.setActiveMenu();
    app.logic.languageSelect();
};

$(function() {
    app.init();
});
