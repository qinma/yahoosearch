The repository is for this homework:
Search on yahoo by generated search string. Click on the 3rd result which is not video. Open the page and take a screenshot.

Details:
Write an automated test case using any testing framework and language of your choice. This automated test case will need to:

    1) Operate in a desktop screen size.
    2) Navigate to www.yahoo.com
    3) Enter in a search query into the search box that follows the following rules: 
    	a)it will be composed of the following search terms where: 
   	     i) the first term is randomly selected from this list: ["red", "green", "blue"] 
             ii) the next few terms are "is the color of" 
    	     iii) this is followed by "the" or "a" if needed by the last term iv) and the last term is randomly selected from ["grass", "rainbow", "turtle", "unicorn"] 
        b) Examples of good randomly generated search queries are (note, they don't make sense, are not meant to make sense.: 
    	     i) "red is the color of a turtle" 
    	     ii) "green is the color of grass" 
    	     iii) "blue is the color of the rainbow"
    4) Submit the query
    5) Select the 3rd regular result in the results. (Not an ad result, and not video results). For clarity, look at the final screenshot no this page for an example.
    6) Once it has navigated to the new page, take a screenshot.
