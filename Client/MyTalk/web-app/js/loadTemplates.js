// posto non adeguato per questi oggetti
loadTemplates = function(files) {
    var dataTemplateName;
    $(files).each(function() {
        
        var scriptObj = $('<script>');
        scriptObj.attr('type', 'text/x-handlebars');
        dataTemplateName = this.substring(0, this.indexOf('.'));
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



// posto e nome file non adeguato
if (typeof jQuery !== 'undefined') {
    (function($) {
        $('#spinner').ajaxStart(function() {
            $(this).fadeIn();
        }).ajaxStop(function() {
            $(this).fadeOut();
        });
    })(jQuery);
}

/*
loadTemplates([
  'index.hbs',
  'content.hbs',
  'lists.hbs',
  'lists_t.hbs',
  '_header.hbs',
  '_dashboard.hbs',
  '_login.hbs',
  '_register.hbs',
  '_description.hbs',
  '_callip.hbs'
]);
*/
