// posto non adeguato per questi oggetti

MyTalk.loadTemplates = function(files) {
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

setTimeout(function(){ // Sistemare immediatamente
    $.getScript('graphics_js/animation-c2.js');
    $('#menu_area').css('display','block');
    $.getScript('graphics_js/animation-c4.js');
},2000);


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
