/* jshint esversion: 6 */

document.getElementById('searchbutton').onclick = () => {
    let searchTerm = encodeURIComponent(document.getElementById('searchbox').value);
    fetch("/search?q=" + searchTerm)
    .then((response) => response.json())
    .then((data) => {
        if (data[0] && data[0].message) {
            document.getElementById("responsesize").innerHTML = 
                `<p>${data[0].message}</p>`;
            document.getElementById("urllist").innerHTML = `<ul></ul>`;
        } else {
            document.getElementById("responsesize").innerHTML = 
                `<p>${data.length} websites retrieved</p>`;

                let results = data.map((page) =>
                `<li><a href="${page.url}">${page.title}</a></li>`)
                .join("\n");
            document.getElementById("urllist").innerHTML = `<ul>${results}</ul>`;
        }
        
    });
};