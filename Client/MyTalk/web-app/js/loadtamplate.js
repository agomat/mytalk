var templates = {

    files : [
    'index.hbs',
    'login.hbs',
    'logged.hbs',
    ]
};

loadTemplates(templates.files);

function loadTemplates(files) {
    
    $(files).each(function() {
        
        var scriptObj = $('<script>');
        scriptObj.attr('type', 'text/x-handlebars');
        var dataTemplateName = this.substring(0, this.indexOf('.'));
        scriptObj.attr('data-template-name', dataTemplateName);
        //alert(dataTemplateName);
            
        $.ajax({
            async: false,
            type: 'GET',
            url: 'js/view/templates/' + this,
            success: function(resp) {
                scriptObj.html(resp);
                $('body').append(scriptObj);    
                alert(resp);       
            }
        });
    });
}