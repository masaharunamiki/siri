$("#word").change(function(){
    if ($("#last").val() != "" && $("#last").val() != $(this).val().slice(0, 1)) {
        alert("一致しません");
        return false;
    }
    var data = {
        "word": $(this).val()
    };

    // 通信実行
    $.ajax({
        type:"get",
        url:"/api/google/search",
        data:data,
        success: function(answer) {
            // Success
            $("#answer").html(answer);
            if (answer == "You Win." || answer == "You Lose.") {
                return;
            }
            var wordObj = $("#word");
            wordObj.val("");
            var last = answer.slice(-1);
            if (last == "ー") {
                last = answer.slice(-2, -1);
            }
            wordObj.attr("placeholder", last + "からは始まる日本語");
            wordObj.focus();
            $("#last").val(last);
        },
        error : function(answer) {
            $("#answer").html("crazy Network!!");
        }
    });
});

$("#pushHint").click(function(){
    $("#hint_result").empty();
    var data = {
        "word": $("#last").val()
    };

    // 通信実行
    $.ajax({
        type:"get",
        url:"/api/google/suggest",
        data:data,
        success: function(response) {
            // Success
            $.each(response, function(i, value) {
                $("#hint_result").append("<li class='selected_word'>" + value + "</li>")
            });
        },
        error : function(answer) {
            $("#answer").html("crazy Network!!");
        }
    });
});

$("#pushTelephone").click(function(){
    window.open("https://line.me/ja/");
    return false;
});
/*
$("#hint_result").on("click",".selected_word",function(){
    $("#word").val($(this).text());
});
*/