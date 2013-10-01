package wallOfTweets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONObject;



@SuppressWarnings("serial")
public class WallServlet extends HttpServlet {
	

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String uri = req.getRequestURI();
		String liked = uri.replaceFirst("/wslab03/wall/like/","");
		if (!uri.equals(liked)) {  // liked == id of the tweet to be liked
			resp.setContentType("text/plain");
			resp.getWriter().println(Database.likeTweet(Long.valueOf(liked)));
		}
		else {
			resp.setContentType("application/json");
			resp.setHeader("Cache-control", "no-cache");
			List<Tweet> tweets= Database.getTweets();
			JSONArray job = new JSONArray();
			for (Tweet t: tweets) {
				JSONObject jt = new JSONObject(t);
				jt.remove("class");
				job.put(jt);
			}
			resp.getWriter().println(job.toString());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		int max_length_of_data = request.getContentLength();
		byte[] httpInData = new byte[max_length_of_data];
		ServletInputStream  httpIn  = request.getInputStream();
		httpIn.readLine(httpInData, 0, max_length_of_data);
		String body = new String(httpInData);
		/*      ^
		      The String variable body contains the sent (JSON) Data. 
		*/
	}

	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		throw new ServletException("DELETE not yet implemented");
	}

}
