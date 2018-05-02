package schedule;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class processSchedule
 */
@WebServlet("/processSchedule")
public class processSchedule extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public processSchedule() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	try
	{
		
		List<String> venuesList = new ArrayList();
		venuesList.add("Hyderabad");
		venuesList.add("Nagpur");
		venuesList.add("Ahmedabad");
		venuesList.add("Lucknow");
		venuesList.add("Mumbai");
		venuesList.add("Kolkata");
		venuesList.add("Haryana");
		venuesList.add("Ranchi");
		venuesList.add("Delhi");
		venuesList.add("Chennai");
		venuesList.add("Jaipur");
		venuesList.add("Pune");
		
		
		List<String> teamList = new ArrayList<>();
		teamList.add("Telugu Titans");
		teamList.add("Bengaluru Bulls");
		teamList.add("Gujarat Fortune Giants");
		teamList.add("UP Yoddha");
		teamList.add("U Mumba");
		teamList.add("Bengal Warriors");
		teamList.add("Haryana Steelers");
		teamList.add("Patna Pirates");
		teamList.add("Dabang Delhi");
		teamList.add("Tamil Thalaivas");
		teamList.add("Jaipur Pink Panthers");
		teamList.add("Puneri Paltan");
		
		List<String> poolA = new ArrayList();
		poolA.add("Delhi");
		poolA.add("Haryana");
		poolA.add("Jaipur");
		poolA.add("Ahmedabad");
		poolA.add("Pune");
		poolA.add("Mumbai");
		
		List<String> poolB = new ArrayList();
		poolB.add("Chennai");
		poolB.add("UP");
		poolB.add("Patna");
		poolB.add("Kolkata");
		poolB.add("Bengaluru");
		poolB.add("Hyderabad");
		
		
		List<String> singleHeaders = new ArrayList();
		singleHeaders.add("Thursday");
		singleHeaders.add("Thursday");
		singleHeaders.add("Thursday");
		singleHeaders.add("Thursday");
		singleHeaders.add("Thursday");
		singleHeaders.add("Thursday");
		singleHeaders.add("Thursday");
		singleHeaders.add("Friday");
		singleHeaders.add("Friday");
		singleHeaders.add("Friday");
		singleHeaders.add("Tuesday");
		singleHeaders.add("Tuesday");
		
		String startDate = "19/10/2018";
		
		Date dt = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
		Calendar c = Calendar.getInstance(); 
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM");
		DateFormat dayFormat = new SimpleDateFormat("EEEE");
		
		JsonArray scheduleList = new JsonArray();		
		for(int venueCount=0;venueCount<venuesList.size();venueCount++)
		{
			boolean isFirstDayofVenue = true;
			for(int dayCount=1;dayCount<=7;dayCount++)
			{
				JsonObject scheduleRow = new JsonObject();
				if(dayFormat.format(dt).toString().equals("Monday"))
				{
					scheduleRow.addProperty("venue",venuesList.get(venueCount).toString());
					scheduleRow.addProperty("date", dateFormat.format(dt));
					scheduleRow.addProperty("day", dayFormat.format(dt));
					scheduleRow.addProperty("m1ta", "");
					scheduleRow.addProperty("m1tb", "");
					scheduleRow.addProperty("m2ta", "");
					scheduleRow.addProperty("m2tb", "");					
				}
				else
				{
					String m1tA ="";
					String m1tB ="";					
					String m2tA ="";
					String m2tB ="";
					
					if(dayFormat.format(dt).toString().equals(singleHeaders.get(venueCount)))
					{
						//Check if the previous day match played at same venue. This is to know whether this is the first match being played at the venue
						//If this is the first day at venue then make the Match 2 as empty for single header because match 1 will be reserved for home team
						if(scheduleList.size()>1 && scheduleList.get(scheduleList.size()-1).getAsJsonObject().get("venue").getAsString().equals(venuesList.get(venueCount)))
						{
							m1tA="No Match (Single Header)";
							m1tB="No Match (Single Header)";
						}
						else
						{
							m2tA="No Match (Single Header)";
							m2tB="No Match (Single Header)";							
						}
					}
					if(venueCount==0 && dayCount==1)
					{
						m1tA="Telugu Titans";
						m1tB ="Tamil Thalaivas";					
						m2tA ="U Mumba";
						m2tB ="Puneri Paltan";
					}
					else
					{
						m1tA=m1tA.equals("")? dayCount==1?teamList.get(venueCount).toString():"":m1tA;
						m1tB =m1tB.equals("")?"":m1tB;					
						m2tA =m2tA.equals("")?dayCount!=1?teamList.get(venueCount).toString():"":m2tA;
						m2tB =m2tB.equals("")?"":m2tB;						
					}
					
					scheduleRow.addProperty("venue",venuesList.get(venueCount).toString());
					scheduleRow.addProperty("date", dateFormat.format(dt));
					scheduleRow.addProperty("day", dayFormat.format(dt));
					scheduleRow.addProperty("m1ta", m1tA);
					scheduleRow.addProperty("m1tb", m1tB);
					scheduleRow.addProperty("m2ta", m2tA);
					scheduleRow.addProperty("m2tb", m2tB);
					
					isFirstDayofVenue = false;
				}
				scheduleList.add(scheduleRow);
				
				c.setTime(dt);
				c.add(Calendar.DATE, 1);
				dt = c.getTime();
			}
		}
		
	PrintWriter writer = response.getWriter();
	String json = new Gson().toJson(scheduleList);
	writer.print(json);
	}
	catch(Exception ex)
	{
		System.out.println(ex.getMessage());
	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
