<%@page import="rocks.teammolise.myunimol.webapp.UserInfo"%>
<%@page import="rocks.teammolise.myunimol.jsputils.JspUtils"%>
<% 
	JspUtils utils = new JspUtils(request, response, session, out);
	
	if (!utils.checkLogin())
        return;
%>

<html>
    <head>
        <title>Appelli</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<% 
        	utils.writeStandardImports();
        %>
        
        <link rel='import' href='bower_components/core-header-panel/core-header-panel.html' />
        <link rel='import' href='bower_components/core-drawer-panel/core-drawer-panel.html' />
        <link rel='import' href='bower_components/core-menu/core-menu.html' />
        <link rel='import' href='bower_components/core-drawer-panel/core-drawer-panel.html' />
        <link rel='import' href='bower_components/core-item/core-item.html' />
        <link rel='import' href='bower_components/core-icon-button/core-icon-button.html' />
        <link rel='import' href='bower_components/core-toolbar/core-toolbar.html' />
        <link rel='import' href='bower_components/core-icons/core-icons.html' />
        <link rel='import' href='bower_components/core-icon/core-icon.html' />
        <link rel='import' href='bower_components/paper-icon-button/paper-icon-button.html' />
        <link rel="import" href="our_components/gw-session/gw-session.html" />
        <link rel='import' href='our_components/myunimol-ajax/myunimol-ajax.html' />
        
        <style type="text/css">
        	#no-sessions {
		    	position: relative;
		    	display: block;
		    	width: 60%;
		    	margin: 20%;
		    	color: #526E9C;
		    	text-align: center;
		    }
		    #no-sessions > img {
		    	width: 50px;
		    }
        </style>
    </head>

	<body>
    	<myunimol-ajax auto
    		id='ajax' 
    		method='POST'
    		url="GetExamSessionsServlet" 
    		params='{}' 
    		handleAs='json'>
    	</myunimol-ajax>

		<core-drawer-panel id="drawerPanel">
			<% utils.writeLeftMenu("Appelli", 3); %>
			
		  	<core-header-panel main>
		  		<core-toolbar id="mainheader">
		      		<paper-icon-button id="navicon" icon="menu"></paper-icon-button>
		      		<span flex style="font-size: 28;">
		      			<strong>Appelli</strong>
		      		</span>
		    	</core-toolbar>
		    	<div id='activeContentHandler' class="content">
					<gw-session id='gwsession'></gw-session>
					<div class="centeredMessage" id="no-sessions" style="display: none;">
                		<img src="img/swag.png" alt="swag emoji" />
                	<p>Al momento non sono disponibili appelli...</p>
                </div>
		    	</div>
		  	</core-header-panel>
		  	
		</core-drawer-panel>
	    <script>
	    	document.addEventListener('polymer-ready', function() {
	            var ajax = document.getElementById("ajax");
	            
	            ajax.addEventListener("core-response", function (event) {
	            	if (event.detail.response.exams.length == 0) {
	            		document.getElementById("no-sessions").style.display = "block";
	            	} else {
	            		document.getElementById("no-sessions").style.display = "none";
	            		document.getElementById("gwsession").sessions = event.detail.response.exams;
	            	}
	            });
	        });
	    </script>
</body>
</html>