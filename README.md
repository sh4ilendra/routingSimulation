# routingSimulation
Spring boot app for REST API getting locations between source and destination

You are given two locations - A & B. You are provided with latitude & longitude of them (short form
LatLngs). You need to calculate ‘real’ points on the road that connects A & B. You need to use data from
google directions API .


Sample Input: LatLngs for point A and point B.
A is 12.93175, 77.62872 and B is 12.92662, 77.63696

Sample Output: LatLngs between A & B on the road.
12.93175, 77.62872
12.93166, 77.62852
12.93125, 77.62870
..
12.92713, 77.63719
12.92668, 77.63717
12.92662, 77.63696


Steps to initiate project:
1. Get API Key from Google Maps console and update api key in "src/main/resources/application.properties"
2. Start the project and start the tomcat server locally.
3. Send the request as below:
   http://localhost:8080/getPoints?origin=12.93175,77.62872&destination=12.92662,77.63696
