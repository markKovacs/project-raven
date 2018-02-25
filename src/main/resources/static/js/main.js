var app = app || {};

app.init = function() {
    console.log("main.js imported");
    app.logic.testFunction();
};

$(function() {
    app.init();
});
