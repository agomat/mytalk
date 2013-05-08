function loadTemplates(files) {
    
    $(files).each(function() {
        
        var scriptObj = $('<script>');
        scriptObj.attr('type', 'text/x-handlebars');
        var dataTemplateName = this.substring(0, this.indexOf('.'));
        scriptObj.attr('data-template-name', dataTemplateName);
            
        $.ajax({
            async: false,
            type: 'GET',
            url: 'js/view/templates/' + this,
            success: function(data) {
              $(data).filter('script[type="text/x-handlebars"]').each(function() {
                templateName = $(this).attr('data-template-name');
                Ember.TEMPLATES[templateName] = Ember.Handlebars.compile($(this).html());
              });
            }
        });
    });
}
function loadTemplates_DEPRECATED(files) {
    
    $(files).each(function() {
        
        var scriptObj = $('<script>');
        scriptObj.attr('type', 'text/x-handlebars');
        var dataTemplateName = this.substring(0, this.indexOf('.'));
        scriptObj.attr('data-template-name', dataTemplateName);
            
        $.ajax({
            async: false,
            type: 'GET',
            url: 'js/view/templates/' + this,
            success: function(resp) {
                scriptObj.html(resp);
                $('body').append(scriptObj);         
            }
        });
    });
}
