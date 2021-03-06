
function toggleActionIcons(elementId) {
    var elem = $(elementId);
    if (elem.style.display == '') {
        elem.style.display = 'none';
    } else {
        elem.style.display = '';
    }
}


function deleteConfirmation() {
    return !confirm('Kaydı silmek istediğinizden emin misiniz?');
}


function openHelp() {
    newwin = window.open(null, 'HelpWinId', 'width=850,height=600,scrollbars=yes,resizable=yes,status=no,toolbar=no,location=no,menubar=no,directories=no')
    if (window.focus) {
        newwin.focus()
    }

}


function enableRibbonBtn(ids) {
    jQuery(ids).each(function() {
        jQuery(this).enable();
    });
}

function disableRibbonBtn(ids) {
    jQuery(ids).each(function() {
        jQuery(this).disable();
    });
}


function bindEnters(){
    jQuery(window).off('.telve').on('keydown.telve', function (e) {
        if ( !e.ctrlKey && e.keyCode == 13 ) {
            e.preventDefault();
            e.stopImmediatePropagation();
            return false;
        }
    });
    
    jQuery('.ui-autocomplete-input').off('.telve').on('keyup.telve', function (e) {
        if ( !e.ctrlKey && e.keyCode == 13 ) {
            e.preventDefault();
            e.stopImmediatePropagation();
            return false;
        }
    });
    
    
    jQuery(window).on('keyup.telve', function (e) {
        if ( !e.ctrlKey && e.keyCode == 13 ) {
            e.preventDefault();
            e.stopImmediatePropagation();
            return false;
        }
    });
    
    jQuery(window).on('keypress.telve', function (e) {
        if ( !e.ctrlKey && e.keyCode == 13 ) {
            e.preventDefault();
            e.stopImmediatePropagation();
            return false;
        } else if( e.ctrlKey && e.keyCode == 10 ){
            
            if( jQuery(e.target).parent().hasClass('ui-input-group') || jQuery(e.target).parent().parent().hasClass('ui-input-group') ){
                jQuery(e.target).parent().parent().find('button:first').click();
                e.preventDefault();
                e.stopImmediatePropagation();
                return false;
            }
        } else if( e.ctrlKey && e.keyCode == 13 ){
            
            if( jQuery(e.target).parent().hasClass('ui-input-group') || jQuery(e.target).parent().parent().hasClass('ui-input-group') ){
                jQuery(e.target).parent().parent().find('button:first').click();
                e.preventDefault();
                e.stopImmediatePropagation();
                return false;
            }
        }
    });
}

