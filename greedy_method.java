import java.io.*;
import java.util.*;

/**
 * Created by Laboni on 1/30/2018.
 */
public class Main {
    public static void main(String[] args) {
        //int numberOfSensors = 0,numberOfTargets = 0,length = 1000;
        int length = 1000;
        //int[] sensorNumbers = new int[16];
        int[] sensorNumbers = {375, 400};
        //for(int i = 0; i < 16; i++) sensorNumbers[i] = 25*(i+1);
        int[] targetNumbers = {225};
        //int[] targetNumbers = {15};
        double range = 100.0,theta = 45.0;
        Scanner scanner=new Scanner(System.in);
        try {
            /*File file = new File("Parameter.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            numberOfSensors = Integer.parseInt(bufferedReader.readLine());
            numberOfTargets = Integer.parseInt(bufferedReader.readLine());
            length = Integer.parseInt(bufferedReader.readLine());
            range = Double.parseDouble(bufferedReader.readLine());
            theta = Double.parseDouble(bufferedReader.readLine());

            bufferedReader.close();
            fileReader.close();*/



            for(int numberOfTargets: targetNumbers){
                for(int numberOfSensors: sensorNumbers){
                    for(int l = 3; l <= 3; l++){
                        System.out.println("current configuration:");
                        System.out.println("number of sensors: " + numberOfSensors + "\nnumber of targets: " + numberOfTargets + "\nlength: " + length + "\nrange: " + range + "\ntheta: " + theta);
                        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("new_result_greedy_withSensor.txt"), true));

                        ArrayList<Point> positionsGenerated = new ArrayList<>();
                        File fileSensor = new File("E:\\My_Level_4_Term_2\\CSE_400_Thesis\\Thesis_version2\\Sensors\\sensor-"+numberOfSensors+"-"+l+".txt");
                        //File fileSensor = new File("E:\\My_Level_4_Term_2\\CSE_400_Thesis\\Thesis_version2\\sensorPositions.txt");
                        BufferedReader brSensor = new BufferedReader(new FileReader(fileSensor));
                        String line;
                        for (int i = 0; i < numberOfSensors; i++) {
                            line = brSensor.readLine();
                            //System.out.println(i+"------------copying sensor positions---------");
                            if (line == null) break;
                            StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
                            positionsGenerated.add(new Point(Double.parseDouble(stringTokenizer.nextToken()), Double.parseDouble(stringTokenizer.nextToken())));
                        }
                        brSensor.close();
                        File fileTarget = new File("E:\\My_Level_4_Term_2\\CSE_400_Thesis\\Thesis_version2\\Targets\\target-"+numberOfTargets+".txt");
                        //File fileTarget = new File("E:\\My_Level_4_Term_2\\CSE_400_Thesis\\Thesis_version2\\targetPositions.txt");
                        BufferedReader brTarget = new BufferedReader(new FileReader(fileTarget));
                        for (int i = 0; i < numberOfTargets; i++) {
                            line = brTarget.readLine();
                            if (line == null) break;
                            //System.out.println(i+"------------copying target positions---------");
                            StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
                            positionsGenerated.add(new Point(Double.parseDouble(stringTokenizer.nextToken()), Double.parseDouble(stringTokenizer.nextToken())));
                        }
                        brTarget.close();
                        System.out.println("------------copied current positions---------");
            /*int deploy = scanner.nextInt();

            if (deploy == 1) {
                System.out.println("Enter 1: add 5 sensors\nEnter 2: add 10 targets\nEnter 3: change range\nEnter 4: change theta\nEnter 5: new Sample");
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        numberOfSensors += 5;
                        BufferedWriter bwSensor = new BufferedWriter(new FileWriter(fileSensor, true));
                        generatingAdditionalPositions(bwSensor, positionsGenerated, length, 5);
                        bwSensor.close();
                        break;
                    case 2:
                        numberOfTargets += 10;
                        BufferedWriter bwTarget = new BufferedWriter((new FileWriter(fileTarget, true)));
                        generatingAdditionalPositions(bwTarget, positionsGenerated, length, 10);
                        bwTarget.close();
                        break;
                    case 3:
                        range = scanner.nextDouble();
                        break;
                    case 4:
                        theta = scanner.nextDouble();
                        break;
                    case 5:
                        BufferedWriter bwSensorNew = new BufferedWriter(new FileWriter(fileSensor));
                        generatingAdditionalPositions(bwSensorNew, positionsGenerated, length, numberOfSensors);
                        bwSensorNew.close();
                        BufferedWriter bwTargetNew = new BufferedWriter(new FileWriter(fileTarget));
                        generatingAdditionalPositions(bwTargetNew, positionsGenerated, length, numberOfTargets);
                        bwTargetNew.close();
                }

            }*/
                        //printWriter.close();
                        //BufferedReader br = new BufferedReader(new FileReader(new File("positions.txt")));

                        bw.append("number of sensors: " + numberOfSensors + " number of targets: " + numberOfTargets + " length: " + length + " range: " + range + " theta: " + theta);
                        bw.newLine();

            /*BufferedWriter bwParameter = new BufferedWriter(new FileWriter(new File("Parameter.txt")));
            bwParameter.write(String.valueOf(numberOfSensors));
            bwParameter.newLine();
            bwParameter.write(String.valueOf(numberOfTargets));
            bwParameter.newLine();
            bwParameter.write(String.valueOf(length));
            bwParameter.newLine();
            bwParameter.write(String.valueOf(range));
            bwParameter.newLine();
            bwParameter.write(String.valueOf(theta));
            bwParameter.newLine();
            bwParameter.close();*/

                        BufferedReader brSensorNew = new BufferedReader(new FileReader(fileSensor));
                        BufferedReader brTargetNew = new BufferedReader(new FileReader(fileTarget));
                        Deployment deployment = new Deployment(length, length, numberOfSensors, numberOfTargets);
                        deployment.deploySensorsTargets(brSensorNew, brTargetNew, range, theta);
                        brSensorNew.close();
                        brTargetNew.close();

                        double d = 360/theta;
                        int n = (int)  d; //Number of sectors in a sensor
                        ArrayList<Target>targetArrayListCopy=new ArrayList<>();
                        ArrayList<Target>targetArrayListCopy1=new ArrayList<>();
                        for(Target target: deployment.targetArrayList)
                        {
                            targetArrayListCopy.add(target);
                            targetArrayListCopy1.add(target);
                        }
                        for (Sensor sensor: deployment.sensorArrayList) {
                            sensor.inRangeTargets = new ArrayList[n];
                            for(int i = 0; i<n; i++)
                                sensor.inRangeTargets[i] = new ArrayList<>();
                            for(Target target: deployment.targetArrayList){
                                // targetArrayListCopy.add(target);
                                for(int i = 0; i<n; i++) {
                                    boolean b = sensor.targetInSector(target, i);
                                    // System.out.println("sector "+i+": "+b);
                                    if (sensor.targetInSector(target,i))
                                    {
                                        //System.out.println(sensor.targetInSector(target,i));
                                        sensor.inRangeTargets[i].add(target);
                                        SensorOrientation sensorOrientation=new SensorOrientation();
                                        sensorOrientation.SensorId=sensor.sensorID;
                                        sensorOrientation.Orientation=i;
                                        target.sensorOrientations.add(sensorOrientation);
                                    }
                                }
                            }
                        }
                        //For Greedy Approach Array making
                        //  SensorWithOrientation[]sensorWithOrientations=new SensorWithOrientation[numberOfSensors*n];
                        ArrayList<SensorWithOrientation>sensorWithOrientationArrayList=new ArrayList<>();
                        ArrayList<Sensor>sensorList=new ArrayList<>();
                        for (Sensor sensor: deployment.sensorArrayList) {
                            System.out.println("sensor id "+sensor.sensorID);
                            sensorList.add(sensor);
                            for(int i=0;i<n;i++) {
                                SensorWithOrientation sensorWithOrientation=new SensorWithOrientation();
                                sensorWithOrientation.sensorID=sensor.sensorID;
                                sensorWithOrientation.numberOftagetsCovered = sensor.inRangeTargets[i].size();
                                // sensorWithOrientation.targetList=new Target[sensorWithOrientation.numberOftagetsCovered];
                                sensorWithOrientation.targetArrayList=new ArrayList<>();
                                //for printing target id
                                for(int j=0;j<sensor.inRangeTargets[i].size();j++)
                                {
                                    //   System.out.print("target in sector "+i+" : "+sensor.inRangeTargets[i].get(j).targetID+" ");
                                    // sensorWithOrientation.targetList[j]=sensor.inRangeTargets[i].get(j);
                                    sensorWithOrientation.targetArrayList.add(sensor.inRangeTargets[i].get(j));
                                }

                                //  System.out.println();
                                //printing target id
                                sensorWithOrientation.orientation = i;
                                sensorWithOrientationArrayList.add(sensorWithOrientation);
                            }
                        }
                        QuickSort quickSort=new QuickSort();
                        quickSort.print(sensorWithOrientationArrayList);


                        //******Greedy Approach*****
                        quickSort.sort(sensorWithOrientationArrayList,0,sensorWithOrientationArrayList.size()-1);
                        System.out.println("================Sorted===================");
                        quickSort.print(sensorWithOrientationArrayList);
                        int totalCoveredTargets=0;
                        int sensorUsed=0;
                        while(true)
                        {
                            totalCoveredTargets=totalCoveredTargets+sensorWithOrientationArrayList.get(0).numberOftagetsCovered;
                            //newly added for the used sensor count
                            if(sensorWithOrientationArrayList.get(0).numberOftagetsCovered!=0)
                                sensorUsed++;
                            //==========================================
                            SensorWithOrientation sensorWithOrientation=sensorWithOrientationArrayList.get(0);
                            Sensor sensor=deployment.sensorArrayList.get(sensorWithOrientationArrayList.get(0).sensorID);
                            System.out.println("Selected Sensor "+sensor.sensorID);
                            int orientation=sensorWithOrientationArrayList.get(0).orientation;
                            int targetsCovered=sensorWithOrientationArrayList.get(0).numberOftagetsCovered;
                            int s=sensorWithOrientationArrayList.size();
                            ArrayList<Integer> indexes=new ArrayList<Integer>();
                            for(int i=0;i<s;i++)
                            {
                                if(sensorWithOrientationArrayList.get(i).sensorID==sensor.sensorID)
                                    indexes.add(i);
                            }
                            //  System.out.println("indexes size "+indexes.size());
                            for(int i=0;i<indexes.size();i++)
                            {
                                //  System.out.println("index "+indexes.get(i));
                                sensorWithOrientationArrayList.remove(indexes.get(i).intValue()-i);
                                //  quickSort.print(sensorWithOrientationArrayList);
                            }
                            System.out.println("After removing "+sensor.sensorID);
                            quickSort.print(sensorWithOrientationArrayList);
                            System.out.println("Targets covered "+targetsCovered+" from sensor "+sensor.sensorID);
                            s=sensorWithOrientationArrayList.size();
                            // System.out.println("-------------------problem zone-----------------------------");
                            for(int i=0;i<targetsCovered;i++)
                            {
                                //  System.out.println("target from removed sensor :: "+sensorWithOrientation.targetList[i].targetID);
                                //  System.out.println("target from removed sensor :: "+sensorWithOrientation.targetArrayList.get(i).targetID);
                                for(int j=0;j<s;j++)
                                {
                                    // System.out.print("checking "+sensorWithOrientationArrayList.get(j).sensorID+" orientation "+sensorWithOrientationArrayList.get(j).orientation);
                                    boolean b=deployment.sensorArrayList.get(sensorWithOrientationArrayList.get(j).sensorID).targetInSector(sensorWithOrientation.targetArrayList.get(i),sensorWithOrientationArrayList.get(j).orientation);
                                    //  System.out.println(" target present "+b);
                                    if(b)
                                    {
                                        sensorWithOrientationArrayList.get(j).numberOftagetsCovered--;
                                        sensorWithOrientationArrayList.get(j).targetArrayList.remove(sensorWithOrientation.targetArrayList.get(i));
                                    }
                                    //   quickSort.print(sensorWithOrientationArrayList);

                                }
                                //System.out.println("target removed "+sensor.inRangeTargets[orientation].get(i).targetID);
                                //System.out.println("size of targetlist "+targetArrayListCopy.size()+" removed "+sensorWithOrientation.targetList[i].targetID);
                                targetArrayListCopy.remove(sensorWithOrientation.targetArrayList.get(i));
                                //System.out.println("after removing size of targetlist "+targetArrayListCopy.size());
                            }
                            //  System.out.println("---------------end problem zone----------------------------");
                            System.out.println("After removing targets ");
                            quickSort.print(sensorWithOrientationArrayList);
                            if(targetArrayListCopy.size()==0||sensorWithOrientationArrayList.size()==0)
                                break;
                            quickSort.sort(sensorWithOrientationArrayList,0,sensorWithOrientationArrayList.size()-1);
                            System.out.println("After sorting ");
                            quickSort.print(sensorWithOrientationArrayList);

                        }
                        System.out.println("Total covered finally "+totalCoveredTargets);
                        System.out.println("Total uncovered "+(numberOfTargets-totalCoveredTargets));
                        System.out.println("sensors Used "+sensorUsed+" unused sensor "+(numberOfSensors-sensorUsed));
          /*  System.out.println("======================================================");
            for(int i=0;i<numberOfSensors;i++)
            {
                for(int j=0;j<numberOfSensors;j++)
                {
                    if(conflictGraph.graphMatrix[i][j]==null)continue;
                    System.out.print("Sensor1 "+i+" Sensor2 "+j);
                    System.out.println("  Total conflict : "+conflictGraph.graphMatrix[i][j].totalConflict);
                    Set set1=conflictGraph.graphMatrix[i][j].edgeIntegerHashMap.entrySet();
                    Iterator iterator1=set1.iterator();
                    while (iterator1.hasNext())
                    {
                        Map.Entry mentry=(Map.Entry)iterator1.next();
                        int key=(int)mentry.getKey();
                        System.out.print("( Ori1: "+key/n+" Ori2: "+key%n);
                        System.out.println(" Value: "+mentry.getValue()+" ) ");
                    }
                }
            }
            System.out.println("=================================================================");*/

                        //System.out.println("targets covered "+(numberOfTargets-targetArrayListCopy1.size()));
                        //System.out.println("Remaining uncovered "+targetArrayListCopy1.size());
                        bw.append("greedy-> covered: "+(totalCoveredTargets)+" uncovered: "+(numberOfTargets-totalCoveredTargets));
                        bw.newLine();
                        bw.append("total_used: "+(sensorUsed)+",total_unused: "+(numberOfSensors - sensorUsed));
                        bw.newLine();
                        bw.close();
                    }
                }
            }


        //end of conflict approach
        //******Greedy Approach*****
     /*   quickSort.sort(sensorWithOrientationArrayList,0,sensorWithOrientationArrayList.size()-1);
        System.out.println("================Sorted===================");
        quickSort.print(sensorWithOrientationArrayList);
        int totalCoveredTargets=0;
        while(true)
        {
            totalCoveredTargets=totalCoveredTargets+sensorWithOrientationArrayList.get(0).numberOftagetsCovered;
            SensorWithOrientation sensorWithOrientation=sensorWithOrientationArrayList.get(0);
            Sensor sensor=deployment.sensorArrayList.get(sensorWithOrientationArrayList.get(0).sensorID);
            System.out.println("Selected Sensor "+sensor.sensorID);
            int orientation=sensorWithOrientationArrayList.get(0).orientation;
            int targetsCovered=sensorWithOrientationArrayList.get(0).numberOftagetsCovered;
            int s=sensorWithOrientationArrayList.size();
            ArrayList<Integer> indexes=new ArrayList<Integer>();
            for(int i=0;i<s;i++)
            {
                if(sensorWithOrientationArrayList.get(i).sensorID==sensor.sensorID)
                    indexes.add(i);
            }
            System.out.println("indexes size "+indexes.size());
            for(int i=0;i<indexes.size();i++)
            {
              //  System.out.println("index "+indexes.get(i));
                sensorWithOrientationArrayList.remove(indexes.get(i).intValue()-i);
              //  quickSort.print(sensorWithOrientationArrayList);
            }
            System.out.println("After removing "+sensor.sensorID);
            quickSort.print(sensorWithOrientationArrayList);
            System.out.println("Targets covered "+targetsCovered+" from sensor "+sensor.sensorID);
            s=sensorWithOrientationArrayList.size();
           // System.out.println("-------------------problem zone-----------------------------");
            for(int i=0;i<targetsCovered;i++)
            {
              //  System.out.println("target from removed sensor :: "+sensorWithOrientation.targetList[i].targetID);
              //  System.out.println("target from removed sensor :: "+sensorWithOrientation.targetArrayList.get(i).targetID);
                for(int j=0;j<s;j++)
                {
                   // System.out.print("checking "+sensorWithOrientationArrayList.get(j).sensorID+" orientation "+sensorWithOrientationArrayList.get(j).orientation);
                    boolean b=deployment.sensorArrayList.get(sensorWithOrientationArrayList.get(j).sensorID).targetInSector(sensorWithOrientation.targetArrayList.get(i),sensorWithOrientationArrayList.get(j).orientation);
                  //  System.out.println(" target present "+b);
                    if(b)
                    {
                        sensorWithOrientationArrayList.get(j).numberOftagetsCovered--;
                        sensorWithOrientationArrayList.get(j).targetArrayList.remove(sensorWithOrientation.targetArrayList.get(i));
                    }
                 //   quickSort.print(sensorWithOrientationArrayList);

                }
                //System.out.println("target removed "+sensor.inRangeTargets[orientation].get(i).targetID);
                //System.out.println("size of targetlist "+targetArrayListCopy.size()+" removed "+sensorWithOrientation.targetList[i].targetID);
                targetArrayListCopy.remove(sensorWithOrientation.targetArrayList.get(i));
                //System.out.println("after removing size of targetlist "+targetArrayListCopy.size());
            }
          //  System.out.println("---------------end problem zone----------------------------");
            System.out.println("After removing targets ");
            quickSort.print(sensorWithOrientationArrayList);
            if(targetArrayListCopy.size()==0||sensorWithOrientationArrayList.size()==0)
                break;
            quickSort.sort(sensorWithOrientationArrayList,0,sensorWithOrientationArrayList.size()-1);
            System.out.println("After sorting ");
            quickSort.print(sensorWithOrientationArrayList);
        }
        System.out.println("Total covered finally "+totalCoveredTargets);
        System.out.println("Total uncovered "+(numberOfTargets-totalCoveredTargets));
            bw.append("greedy-> covered: "+totalCoveredTargets+" uncovered: "+(numberOfTargets - totalCoveredTargets));
            bw.newLine();
            bw.close();*/
        //================================
        /* for (Sensor sensor: deployment.sensorArrayList) {
            System.out.println(sensor.sensorID+": ");
            for(int i = 0; i<n; i++) {
                System.out.print("Sector " +i+": ");
                for (Target target : sensor.inRangeTargets[i]) {
                    System.out.print(target.targetID + ", ");
                }
                System.out.println();
            }
            System.out.println();
        }*/

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void generatingAdditionalPositions(BufferedWriter bw, ArrayList<Point> positions, int length, int n){
        Random random = new Random();
        try {
            for (int i = 0; i < n; i++) {
                double x = random.nextInt(length);
                double y = random.nextInt(length);
                while (positions.contains(new Point(x, y))) {

                    //x = random.nextInt(length);
                    //y = random.nextInt(length);
                    // deploying sensor & target on floating coordinate in a unit square
                    x = random.nextFloat();
                    y = random.nextFloat();
                }
                bw.append(x + "," + y);
                bw.newLine();
                positions.add(new Point(x, y));
            }
        }catch(Exception e){
            System.out.println(e.getStackTrace());
        }
    }
}
