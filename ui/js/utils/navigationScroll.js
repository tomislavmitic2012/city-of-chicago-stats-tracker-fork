// Needs to refactor later ... 
define(['jquery'], function($) {

    return function NavSideAnimationClick(){
        //scroll to div
        $('body').on('click', '.scroll-link', function(event){
            event.preventDefault();
            var sectionID = $(this).attr("data-id");
            if (!$(this).hasClass('disabled')) {
                scrollToID('#' + sectionID, 750);
            }
        });
    }
});