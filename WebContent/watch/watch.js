console.log('WATCH')
const $ = (id) => {
    return document.getElementById(id);
}
const key = document.location.search;
const mediaId = document.location.search.split('=')[1];

// User type
// fetch('../Auth', {
//     method: 'GET',
//     header: {
//         'Content-type': 'application/x-www-form-urlencoded'
//     },
//     credentials: 'same-origin',
// }).then(response => response.json())
//     .then(data => {
//         const typeUser = data.type_user;
//         if (typeUser === 'Administrator') {
//             console.log('administrator')
//             $('delete-media').style.display = '';
//         }
//     }).catch(error => {
//         typeUser = 'Invited';
//     });
// // Media data
// fetch('../Comments?key=' + mediaId, {
//     method: 'GET',
//     headers: {
//         'Content-type': 'application/x-www-form-urlencoded',
//     },
//     credentials: 'same-origin',
// }).then(response => response.json())
//     .then(res => {
//         const mediaData = res.data,
//             comments = res.comments;
//         if (res.status == 200) {
//             $('media-name').innerText = mediaData.name;
//             $('media-description').innerText = mediaData.description;
//             $('media-user').innerText = mediaData.username;
//             $('media-views').innerText = mediaData.views + ' views';
//             // $('media-date').innerText = mediaData.created;
//             $('like-count').innerText = mediaData.likes;
//             $('dislike-count').innerText = mediaData.dislikes;
//             const template = document.querySelector('#comment-template');
//             comments.forEach(comment => {
//                 let clone = template.content.cloneNode(true);
//                 let username = clone.querySelectorAll('h6')[0];
//                 let date = clone.querySelectorAll('small')[0];
//                 let text = clone.querySelectorAll('p')[0];
//                 username.innerText = comment.username;
//                 date.innerText = comment.created_at;
//                 text.innerText = comment.comment_text;
//                 template.parentNode.appendChild(clone);
//             });
//         } else {
//             console.log(res)
//         }
//     }).catch(error => {
//         console.log(error.message);
//     });
// // Streaming
// $("video").src = "../Streaming" + key;


// ============================= ACTIONS ===============================        
// Function to download the video        
function downloadMedia() {
    var url = "../Download" + document.location.search;
    var downloadWindow = window.open(url);
}
// Do like
function doLike() {
    let data = {
        media_id: mediaId
    }
    let configs = {
        method: 'POST',
        headers: {
            'Content-type': 'application/x-www-form-urlencoded'
        },
        credentials: 'same-origin',
        body: JSON.stringify(data)
    }

    fetch("../Like", configs)
        .then(response => response.json())
        .then(data => {
            // TODO: Hacer algo cuando haya un like
            console.log(data);
        }).catch(error => {
            console.log(error.message);
        })
}
// Do dislike
function doDislike() {
    let data = {
        media_id: mediaId
    }
    let configs = {
        method: 'POST',
        headers: {
            'Content-type': 'application/x-www-form-urlencoded'
        },
        credentials: 'same-origin',
        body: JSON.stringify(data)
    }

    fetch("../Dislike", configs)
        .then(response => response.json())
        .then(data => {
            // TODO: Hacer algo cuando haya un dislike
            console.log(data);
        }).catch(error => {
            console.log(error.message);
        })
}
// Do comment
function doComment() {
    let commentText = document.getElementById('comment').value;
    let data = {
        media_id: mediaId,
        comment_text: commentText
    }
    let configs = {
        method: 'POST',
        header: {
            'Content-type': 'application/x-www-form-urlencoded'
        },
        credentials: 'same-origin',
        body: JSON.stringify(data)
    }
    fetch('../Comments', configs)
        .then(response => response.json())
        .then(data => {
            // TODO: Hacer algo cuando se realiza un comentario
            console.log(data);
        }).catch(error => {
            console.log(error.message);
        });
    commentsBox();
}