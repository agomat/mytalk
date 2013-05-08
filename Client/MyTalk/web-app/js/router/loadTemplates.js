function loadTemplates(files) {
    
    //alert();
    $(files).each(function() {
        
        var scriptObj = $('<script>');
        scriptObj.attr('type', 'text/x-handlebars');
        var dataTemplateName = this.substring(0, this.indexOf('.'));
        scriptObj.attr('data-template-name', dataTemplateName);
            
        $.ajax({
            async: false,
            type: 'GET',
            url: 'js/view/templates/' + this,
            cache: false,
            success: function(data) {
              $(data).filter('script[type="text/x-handlebars"]').each(function() {
                templateName = $(this).attr('data-template-name');
                alert(templateName);
                Ember.TEMPLATES[templateName] = Ember.Handlebars.compile($(this).html());
              });
            }
        });
    });
}