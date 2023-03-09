# MusicWiki
-The app shows detailed information of genre, tracks, artists, albums available on last.fm Api: https://www.last.fm/api  
-The App is based on MVVM pattern.  
Technology Used:  
    ->Retrofit (To fetch data from Api)  
    ->Glide  
    ->Coroutines  


1-Home Page:->  
    -The App opens with a page showing top 10 genres available from api.  
    -With a drop down button to get all the top genres.  
    
2-Genre(Tag) detail screen ->  
    -After selecting particular genre(tag), a tag detailed screen will be shown to the user, which contains:  
       *Three tabs namely : Albums, Artists, Tracks associated with the selected genre(tag)  
       *Genre's short description will be shown.  
    -When the user choose a particular album or artist, user will be redirected to a detailed album/artist screen.  
    
3-Detailed Album Screen ->  
    -This screen will show the image associated with the album if available  
    -Title of the album and artist info if available.  
    -Top genre of the selected album.  
    -Short Description of the album will also be shown if available.  
    -And if user selects any genre from this screen then, genre(tag) detailed screen will be shown.  

4-Detailed Artists Screen ->   
    -This screen will show the image assocuated with the artist, Name of the artist,   
    -Stats: Playcount & Followers  
    -Top genre(tag) list will also be shown if available, and if user selects any genre, genre detailed screen will be opened.  
    -A short description about the artist will be shown if available.  
    -A list of top tracks will also be shown, to the user, with the fuctionality to see details of particular track.  
    -A list of top albums will also be shown, to the user, and if user selects any album, album's detail screen will be shown to the user.  
    
1-HomeScreen  
https://raw.githubusercontent.com/Rz-Mohsin/MusicWiki/master/MusicWiki-HomePage.jpg  
2-HomeScreen-Expanded  
https://raw.githubusercontent.com/Rz-Mohsin/MusicWiki/master/MusicWiki-HomePageExpanded.jpg  
3-Genre(Tag) Details(Albums)  
https://raw.githubusercontent.com/Rz-Mohsin/MusicWiki/master/MusicWiki-ArtistDetails.jpg  
4-Genre(tag) Details(Artists)  
https://raw.githubusercontent.com/Rz-Mohsin/MusicWiki/master/MusicWiki-TagDetails(artists).jpg  
5-Genre(tag) Details(Tracks)  
https://raw.githubusercontent.com/Rz-Mohsin/MusicWiki/master/MusicWiki-TagDetails(tracks).jpg  
6-Album Details  
https://raw.githubusercontent.com/Rz-Mohsin/MusicWiki/master/MusicWiki-AlbumDetails.jpg  
7-Artist Details  
https://raw.githubusercontent.com/Rz-Mohsin/MusicWiki/master/MusicWiki-ArtistDetails.jpg  



   
    
