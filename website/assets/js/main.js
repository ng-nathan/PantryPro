$(function() {
    
    "use strict";
    
    //===== Prealoder
    
    $(window).on('load', function(event) {
        $('.preloader').delay(100).fadeOut(100);
    });
    
    
    //===== Sticky

    $(window).on('scroll', function (event) {
        var currentPage = window.location.href;
        var scroll = $(window).scrollTop();
        if(currentPage.indexOf("postmortem.html") === -1){
            if (scroll < 20) {
                $(".header_navbar").removeClass("sticky");
                $(".header_navbar img").attr("src", "assets/images/navlogowhite.svg");
            } else {
                $(".header_navbar").addClass("sticky");
                $(".header_navbar img").attr("src", "assets/images/navlogo.svg");
            }
        }
    });
    
    
    //===== Section Menu Active

    // var scrollLink = $('.page-scroll');
    // // Active link switching
    // $(window).scroll(function () {
    //     if(scrollLink.length){
    //         var scrollbarLocation = $(this).scrollTop();

    //         scrollLink.each(function () {
    
    //             var sectionOffset = $(this.hash).offset().top - 73;
    
    //             if (sectionOffset <= scrollbarLocation) {
    //                 $(this).parent().addClass('active');
    //                 $(this).parent().siblings().removeClass('active');
    //             }
    //         });
    //     }
    // });

    //===== What we learnt (postmortem)

    var $testimonialsDiv = $('.learnt');
    if ($testimonialsDiv.length && $.fn.owlCarousel) {
        $testimonialsDiv.owlCarousel({
            items: 1,
            nav: true,
            dots: false,
            navText: ['<span class="ti-arrow-left"></span>', '<span class="ti-arrow-right"></span>']
        });
    }
    
    //===== close navbar-collapse when a  clicked

    $(".navbar-nav a").on('click', function () {
        $(".navbar-collapse").removeClass("show");
    });

    $(".navbar-toggler").on('click', function () {
        $(this).toggleClass("active");
    });

    $(".navbar-nav a").on('click', function () {
        $(".navbar-toggler").removeClass('active');
    });
    
    
    //===== Back to top
    
    // Show or hide the sticky footer button
    $(window).on('scroll', function(event) {
        if($(this).scrollTop() > 600){
            $('.back-to-top').fadeIn(200)
        } else{
            $('.back-to-top').fadeOut(200)
        }
    });
    
    
    //Animate the scroll to yop
    $('.back-to-top').on('click', function(event) {
        event.preventDefault();
        
        $('html, body').animate({
            scrollTop: 0,
        }, 1500);
    });
    
    
    //=====  WOW active
    
    var wow = new WOW({
        boxClass: 'wow', //
        mobile: false, // 
    })
    wow.init();
    
}); // End