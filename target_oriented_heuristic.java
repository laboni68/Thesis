import java.io.*;
import java.util.*;

/**
 * Created by Laboni on 1/30/2018.
 */
public class Main {
    public static void main(String[] args) {
        //int numberOfSensors = 0,numberOfTargets = 0,length = 1000;
        int length = 1000;
        int[] sensorNumbers = new int[16];
        //int[] sensorNumbers = {4};
        for(int i = 0; i < 16; i++) sensorNumbers[i] = 25*(i+1);
        int[] targetNumbers = {225};
        //int[] targetNumbers = {5};
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
                    for(int l = 1; l <= 5; l++){
                        System.out.println("current configuration:");
                        System.out.println("number of sensors: " + numberOfSensors + "\nnumber of targets: " + numberOfTargets + "\nlength: " + length + "\nrange: " + range + "\ntheta: " + theta);
                        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("new_result_max_conflict_targetoriented.txt"), true));

                        ArrayList<Point> positionsGenerated = new ArrayList<>();
                        File fileSensor = new File("E:\\My_Level_4_Term_2\\CSE_400_Thesis\\Thesis_version2\\Sensors\\sensor-"+numberOfSensors+"-"+l+".txt");
                        //File fileSensor = new File("E:\\My_Level_4_Term_2\\CSE_400_Thesis\\Thesis\\sensorPositions.txt");
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
                        //File fileTarget = new File("E:\\My_Level_4_Term_2\\CSE_400_Thesis\\Thesis\\targetPositions.txt");
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

                        double d = 360 / theta;
                        int n = (int) d; //Number of sectors in a sensor
                        ArrayList<Target> targetArrayListCopy = new ArrayList<>();
                        ArrayList<Target> targetArrayListCopy1 = new ArrayList<>();
                        for (Target target : deployment.targetArrayList) {
                            targetArrayListCopy.add(target);
                            targetArrayListCopy1.add(target);
                        }
                        for (Sensor sensor : deployment.sensorArrayList) {
                            sensor.inRangeTargets = new ArrayList[n];
                            for (int i = 0; i < n; i++)
                                sensor.inRangeTargets[i] = new ArrayList<>();
                            for (Target target : deployment.targetArrayList) {
                                // targetArrayListCopy.add(target);
                                for (int i = 0; i < n; i++) {
                                    boolean b = sensor.targetInSector(target, i);
                                    // System.out.println("sector "+i+": "+b);
                                    if (sensor.targetInSector(target, i)) {
                                        //System.out.println(sensor.targetInSector(target,i));
                                        sensor.inRangeTargets[i].add(target);
                                        SensorOrientation sensorOrientation = new SensorOrientation();
                                        sensorOrientation.SensorId = sensor.sensorID;
                                        sensorOrientation.Orientation = i;
                                        target.sensorOrientations.add(sensorOrientation);
                                        target.sensorOrientationHashMap.put(sensorOrientation.SensorId, sensorOrientation);
                                    }
                                }
                            }
                        }
                        //For Greedy Approach Array making
                        //  SensorWithOrientation[]sensorWithOrientations=new SensorWithOrientation[numberOfSensors*n];
                        ArrayList<SensorWithOrientation> sensorWithOrientationArrayList = new ArrayList<>();
                        // ArrayList<SensorWithOrientation>sensorWithOrientationArrayListGraph=new ArrayList<>();
                        HashMap<Integer, SensorWithOrientation> sensorWithOrientationHashMap = new HashMap<>();
                        ArrayList<Sensor> sensorList = new ArrayList<>();
                        for (Sensor sensor : deployment.sensorArrayList) {
                            // System.out.println("sensor id "+sensor.sensorID);
                            sensorList.add(sensor);
                            for (int i = 0; i < n; i++) {
                                SensorWithOrientation sensorWithOrientation = new SensorWithOrientation();
                                sensorWithOrientation.sensorID = sensor.sensorID;
                                sensorWithOrientation.numberOftagetsCovered = sensor.inRangeTargets[i].size();
                                // sensorWithOrientation.targetList=new Target[sensorWithOrientation.numberOftagetsCovered];
                                sensorWithOrientation.targetArrayList = new ArrayList<>();
                                //for printing target id
                                for (int j = 0; j < sensor.inRangeTargets[i].size(); j++) {
                                    //   System.out.print("target in sector "+i+" : "+sensor.inRangeTargets[i].get(j).targetID+" ");
                                    // sensorWithOrientation.targetList[j]=sensor.inRangeTargets[i].get(j);
                                    sensorWithOrientation.targetArrayList.add(sensor.inRangeTargets[i].get(j));
                                }

                                //  System.out.println();
                                //printing target id
                                sensorWithOrientation.orientation = i;
                                sensorWithOrientationArrayList.add(sensorWithOrientation);
                                int key = (sensorWithOrientation.sensorID * n) + sensorWithOrientation.orientation;
                                //System.out.print("id "+sensorWithOrientation.sensorID+" ori "+sensorWithOrientation.orientation);
                                //System.out.println("  key "+key);
                                sensorWithOrientationHashMap.put(key, sensorWithOrientation);
                            }
                        }

            /*Set set=sensorWithOrientationHashMap.entrySet();
            Iterator iterator=set.iterator();
            while (iterator.hasNext())
            {
                Map.Entry mentry=(Map.Entry)iterator.next();
                int key=(int)mentry.getKey();
               // System.out.println("key "+key);
                System.out.print("( sensorId: "+key/n+" orientation: "+key%n);
                SensorWithOrientation s1=(SensorWithOrientation)mentry.getValue();
                System.out.println(" Value: "+s1.numberOftagetsCovered+" ) ");
            }*/

                        QuickSort quickSort = new QuickSort();
                        quickSort.print(sensorWithOrientationArrayList);
                        //======================for conflict approach======================================
                        ConflictGraph conflictGraph = new ConflictGraph(numberOfSensors);
                        conflictGraph.GraphModeling(deployment, n);
                        int[] totalConflictSensors = new int[numberOfSensors];
                        int[] totalNonConflictSensors = new int[numberOfSensors];
                        int totalSensor=numberOfSensors;
                        while(targetArrayListCopy1.size()!=0&&totalSensor !=0) {
                            for (int i = 0; i < numberOfSensors; i++) {
                                totalConflictSensors[i] = 0;
                                for (int j = 0; j < numberOfSensors; j++) {
                                    if (conflictGraph.graphMatrix[i][j] == null)
                                    {
                                        //  totalConflictSensors[i] = -100;
                                        //System.out.println("in null i "+i+" j "+j);
                                        continue;
                                    }
                                    if(i==j&&conflictGraph.graphMatrix[i][j].totalConflict>0)
                                    {
                                        System.out.println("non-conflicted sensor " + i + " number : " + conflictGraph.graphMatrix[i][j].totalConflict);
                                        totalNonConflictSensors[i]=conflictGraph.graphMatrix[i][j].totalConflict;
                                        continue;
                                    }
                                    // System.out.println("i "+i+" j "+j);
                                    totalConflictSensors[i] = totalConflictSensors[i] + conflictGraph.graphMatrix[i][j].totalConflict;
                                }
                            }
                            //System.out.println("t "+totalNonConflictSensors[0]+" "+totalNonConflictSensors[1]);
                            Scanner s=new Scanner(System.in);
                            //s.nextInt();
                            int check;
                            for ( check = 0; check < numberOfSensors; check++) {
                                if(totalConflictSensors[check]>0)
                                    break;
                            }
                            //int newTotalConflict=0;
                            for (int i = 0; i < numberOfSensors; i++)
                            {
                                System.out.println("Sensor id " + i + " total conflict " + totalConflictSensors[i]);
                                //  newTotalConflict=newTotalConflict+totalConflictSensors[i];
                            }
                            //System.out.println("new "+newTotalConflict);
                            //Scanner s=new Scanner(System.in);
                            //s.nextInt();
                            if(check==numberOfSensors)
                            {
                                //System.out.println("all zero size "+sensorList.size());
                                // int listSize=sensorList.size();
                                //for(int i=0;i<listSize;i++)

                                System.out.println("in conflict 0");
                                while (sensorList.size()!=0)
                                {
                                    System.out.print("targets ");
                                    for(int i=0;i<targetArrayListCopy1.size();i++)
                                    {
                                        System.out.print(targetArrayListCopy1.get(i).targetID+", ");
                                    }
                                    System.out.println("remaining sensors: "+sensorList.size());
                                    Sensor sensor=sensorList.get(0);
                                    int nowSensor=sensor.sensorID;
                                    int maxTargetsInOrientation = -9999, ori = -1;
                                    for (int k = 0; k < n; k++)//here, n is the total number of orientations
                                    {
                                        //   System.out.println(" ori "+i+" total targets "+sensorWithOrientationHashMap.get(n*maxConflictSensor+i).numberOftagetsCovered);
                                        int targets = sensorWithOrientationHashMap.get(n * nowSensor + k).numberOftagetsCovered;
                                        //System.out.println("sen "+nowSensor+" trget "+targets);
                                        if (maxTargetsInOrientation < targets) {
                                            maxTargetsInOrientation = targets;
                                            ori = k;
                                        }
                                    }
                                    System.out.println("now sensor "+nowSensor+" ori "+ori);
                                    sensorList.remove(sensor);
                                    int coveredTArgets=sensorWithOrientationHashMap.get(n * nowSensor + ori).numberOftagetsCovered;
                                    System.out.println("covers "+coveredTArgets);
                                    for(int i=0;i<coveredTArgets;i++)
                                    {
                                        //System.out.println("in removing...");
                                        //System.out.println(sensorWithOrientationHashMap.get(n * nowSensor + ori).targetArrayList.get(i).targetID);
                                        targetArrayListCopy1.remove(sensorWithOrientationHashMap.get(n * nowSensor + ori).targetArrayList.get(i));
                                    }
                                    if (coveredTArgets!=0)
                                        totalSensor--;
                                }
                                // System.out.println("size of sensor list "+sensorList.size());
                                break;
                            }
                            int max_non_conflict = -99999,nonConflicedSensor=-1;
                            for(int i=0;i<numberOfSensors;i++)
                            {
                                if(totalNonConflictSensors[i]>max_non_conflict)
                                {
                                    max_non_conflict=totalNonConflictSensors[i];
                                    nonConflicedSensor=i;
                                }

                            }

                            /*if(max_non_conflict!=0)
                            {
                                int maxOrientationNonConflictedTargets=-99999,max_ori=-1;
                                System.out.println("got a non-conflicted target in sensor " + nonConflicedSensor);
                                int nonConflictedOriNumber=conflictGraph.graphMatrix[nonConflicedSensor][nonConflicedSensor].edgeIntegerHashMap.size();
                                System.out.println(conflictGraph.graphMatrix[nonConflicedSensor][nonConflicedSensor].edgeIntegerHashMap.size());
                                Set set=conflictGraph.graphMatrix[nonConflicedSensor][nonConflicedSensor].edgeIntegerHashMap.entrySet();
                                Iterator iterator=set.iterator();
                                while (iterator.hasNext())
                                {
                                    Map.Entry mentry=(Map.Entry)iterator.next();
                                    int key=(int)mentry.getKey();
                                    System.out.print("( Ori1: "+key);
                                    System.out.println(" Value: "+mentry.getValue()+" ) ");
                                    if(maxOrientationNonConflictedTargets<(int)mentry.getValue())
                                    {
                                        maxOrientationNonConflictedTargets=(int)mentry.getValue();
                                        max_ori=key;
                                    }
                                }
                                System.out.println("sensor "+nonConflicedSensor+" ori "+max_ori+" non-conflicted "+maxOrientationNonConflictedTargets+" other targets "+sensorWithOrientationHashMap.get(n * nonConflicedSensor + max_ori).numberOftagetsCovered);

                                //s.nextInt();
                            }*/
                            //new from now on
                            int maxConflict = totalConflictSensors[0];
                            int maxConflictSensor = 0;

                            for (int i = 1; i < numberOfSensors; i++) {
                                if (maxConflict < totalConflictSensors[i]) {
                                    maxConflict = totalConflictSensors[i];
                                    maxConflictSensor = i;
                                }
                            }

                            System.out.println("sensor selected is " + maxConflictSensor);
                            //  totalConflictSensors[maxConflictSensor]=-100;
                            int maxTargetsInOrientation = -9999, ori = -1;
                            for (int i = 0; i < n; i++)//here, n is the total number of orientations
                            {
                                System.out.println(" ori "+i+" total targets "+sensorWithOrientationHashMap.get(n*maxConflictSensor+i).numberOftagetsCovered);
                                int targets = sensorWithOrientationHashMap.get(n * maxConflictSensor + i).numberOftagetsCovered;
                                if (maxTargetsInOrientation < targets) {
                                    maxTargetsInOrientation = targets;
                                    ori = i;
                                }
                            }
                            System.out.println("selected orientation " + ori + " with targets " + sensorWithOrientationHashMap.get(n * maxConflictSensor + ori).numberOftagetsCovered);
                            int orientationCovered = sensorWithOrientationHashMap.get(n * maxConflictSensor + ori).numberOftagetsCovered;
                            sensorList.remove(deployment.sensorArrayList.get(maxConflictSensor));//selected sensor is removed from sensor list
                            //sensorList.remove(deployment.sensorArrayList.get(maxConflictSensor));
                            // int conflictedWithArray[]=new int[sensorList.size()];
                            //   int conflictedWithArray[] = new int[numberOfSensors];
                            // ArrayList<Integer>conflictedArraylist=new ArrayList<>();
                            ArrayList<SensorOrientation> conflictSensorOrientaionList = new ArrayList<>();
                            //System.out.println("error 1");
                            System.out.println("--------------conflicted sensors ---------");
                            for (int j = 0; j < numberOfSensors; j++) {
                                //System.out.println("e1 "+j);
                                if(j==maxConflictSensor)
                                    continue;
                                if (conflictGraph.graphMatrix[maxConflictSensor][j]==null)
                                    continue;
                                if(conflictGraph.graphMatrix[maxConflictSensor][j].totalConflict == 0)
                                    continue;
                                // System.out.println("error 2");
                                // conflictedWithArray[j] = -55;//-55 means j has conflict with the removed sensor
                                // conflictedArraylist.add(j);
                                SensorOrientation sensorOrientation = new SensorOrientation();
                                //System.out.println("error 3");
                                sensorOrientation.SensorId = j;
                                //System.out.println("error 4");
                                conflictSensorOrientaionList.add(sensorOrientation);
                                //System.out.print(j+" ");
                                // System.out.println("error 5");
                            }
                            System.out.println();
                            //System.out.println("error 6");
                            System.out.println(" size of conflict " + conflictSensorOrientaionList.size());
                            for (int i = 0; i < orientationCovered; i++) {
                                targetArrayListCopy1.remove(sensorWithOrientationHashMap.get(n * maxConflictSensor + ori).targetArrayList.get(i));
                                //Removing the covered target from target list
                                Target target = sensorWithOrientationHashMap.get(n * maxConflictSensor + ori).targetArrayList.get(i);
                                //System.out.println("for target "+target.targetID);
                                for (int j = 0; j < conflictSensorOrientaionList.size(); j++) {
                                    if (target.sensorOrientationHashMap.get(conflictSensorOrientaionList.get(j).SensorId) == null) {
                                        // System.out.println("no conflict with sensor "+conflictSensorOrientaionList.get(j).SensorId);
                                        conflictSensorOrientaionList.get(j).countingNULL++;
                                        continue;
                                    }
                                    // int orientation =target.sensorOrientationHashMap.get(conflictSensorOrientaionList.get(j).SensorId).Orientation;
                                    //conflictSensorOrientaionList.get(j).Orientation=orientation;
                                    //System.out.println("target "+target.targetID+" is in "+conflictSensorOrientaionList.get(j).Orientation+" of sensor "+conflictSensorOrientaionList.get(j).SensorId);
                                }
                            }
                            for (int i = 0; i < conflictSensorOrientaionList.size(); i++) {
                                if (conflictSensorOrientaionList.get(i).countingNULL == orientationCovered) {
                                    System.out.println("the sensor " + conflictSensorOrientaionList.get(i).SensorId + " will not have any conflict");
                                    conflictSensorOrientaionList.remove(i);
									i--;
                                }
                            }
                            System.out.println("now the conflicted sensor list size " + conflictSensorOrientaionList.size());
                            for (int i = 0; i < numberOfSensors; i++) {
                                conflictGraph.graphMatrix[i][maxConflictSensor] = null;
                                conflictGraph.graphMatrix[maxConflictSensor][i] = null;
                            }
                            for (int i = 0; i < orientationCovered; i++) {
                                Target target = sensorWithOrientationHashMap.get(n * maxConflictSensor + ori).targetArrayList.get(i);
                                //System.out.println("====for target "+target.targetID);
                                for (int j = 0; j < conflictSensorOrientaionList.size(); j++) {
                                    //  System.out.println("sensor "+conflictSensorOrientaionList.get(j).SensorId);
                                    if (target.sensorOrientationHashMap.get(conflictSensorOrientaionList.get(j).SensorId) == null) {
                                        //    System.out.println("no conflict for this target in sensor "+conflictSensorOrientaionList.get(j).SensorId);
                                        continue;
                                    }
                                    int s1 = conflictSensorOrientaionList.get(j).SensorId;
                                    int orientation1 = target.sensorOrientationHashMap.get(s1).Orientation;
                                    sensorWithOrientationHashMap.get(n*conflictSensorOrientaionList.get(j).SensorId+orientation1).targetArrayList.remove(target);
                                    sensorWithOrientationHashMap.get(n*s1+orientation1).numberOftagetsCovered=sensorWithOrientationHashMap.get(n*s1+orientation1).targetArrayList.size();
                                    //System.out.println("before-------------");
                                    for (int k = j+1; k < conflictSensorOrientaionList.size(); k++) {
                                        //  System.out.println("--------in list-----------");
                                        if (conflictSensorOrientaionList.get(j).SensorId == conflictSensorOrientaionList.get(k).SensorId)
                                            continue;
                                        if (target.sensorOrientationHashMap.get(conflictSensorOrientaionList.get(k).SensorId) == null)
                                            continue;

                                        int s2 = conflictSensorOrientaionList.get(k).SensorId;
                                        //System.out.println("s1 "+s1+" s2 "+s2);

                                        int orientation2 = target.sensorOrientationHashMap.get(s2).Orientation;
                                        //sensorWithOrientationHashMap.get(n*conflictSensorOrientaionList.get(j).SensorId+orientation1).numberOftagetsCovered--;
                                        // sensorWithOrientationHashMap.get(n*conflictSensorOrientaionList.get(k).SensorId+orientation2).numberOftagetsCovered--;
                                        sensorWithOrientationHashMap.get(n*conflictSensorOrientaionList.get(k).SensorId+orientation2).targetArrayList.remove(target);
                                        sensorWithOrientationHashMap.get(n*s2+orientation2).numberOftagetsCovered=sensorWithOrientationHashMap.get(n*s2+orientation2).targetArrayList.size();
                                        //System.out.println(conflictGraph.graphMatrix[s1][s2].totalConflict);
                                        conflictGraph.graphMatrix[s1][s2].totalConflict--;
                                        //  System.out.println("target :" + target.targetID + "s1(" + s1 + "," + orientation1 + ") s2(" + orientation2 + "," + orientation2 + ")");
                                        int key = orientation1 * n + orientation2;
                                        int newVal = conflictGraph.graphMatrix[s1][s2].edgeIntegerHashMap.get(key).intValue();
                                        // System.out.println("new "+newVal);
                                        conflictGraph.graphMatrix[s1][s2].edgeIntegerHashMap.put(key, newVal - 1);
                                        conflictGraph.graphMatrix[s2][s1].totalConflict--;
                                        int key2=orientation2*n+orientation1;
                                        int newVal2=conflictGraph.graphMatrix[s2][s1].edgeIntegerHashMap.get(key2).intValue();
                                        conflictGraph.graphMatrix[s2][s1].edgeIntegerHashMap.put(key2,newVal2-1);

                                    }
                                }
                            }

                            totalSensor--;
                            System.out.println("-------end-------------------");
                        }

                        System.out.println("total uncovered "+targetArrayListCopy1.size());
                        System.out.println("total covered "+(numberOfTargets-targetArrayListCopy1.size()));
                        System.out.println("Number of Sensors unused "+totalSensor+" total "+numberOfSensors+" used "+(numberOfSensors-totalSensor));

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
                        bw.append("conflict-> covered: "+(numberOfTargets - targetArrayListCopy1.size())+" uncovered: "+targetArrayListCopy1.size());
                        bw.newLine();
                        bw.append("total_used: "+(numberOfSensors-totalSensor)+",total_unused: "+totalSensor);
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
