var app = app || {};

app.dataHandler = {

    templateAjax: function (testData) {

        $.ajax({
            url: '/api/test',
            method: 'POST',
            dataType: 'json',
            data: {
                test_data: testData
            },
            success: function (response) {
                app.logic.someSuccessHandler();
            },
            error: function () {
                app.logic.someErrorHandler();
            }
        });

    }

};
