package external.windows;

import com.google.protobuf.InvalidProtocolBufferException;
import external.windows.proto.DeviceList;
import external.windows.proto.Update;
import external.windows.proto.UpdateList;

import java.util.Arrays;
import java.util.List;

public class ProtoHelper {
    public static DeviceList getDeviceList(byte[] byteArray, int bytesRead) throws InvalidProtocolBufferException {

        System.out.println("byte read = " + bytesRead);
        return DeviceList.parseFrom(Arrays.copyOfRange(byteArray, 0, bytesRead));
    }


    public static List<Update> getUpdateList(byte[] byteArray, int bytesRead) throws InvalidProtocolBufferException {

        System.out.println("byte read = " + bytesRead);
        UpdateList list = UpdateList.parseFrom(Arrays.copyOfRange(byteArray, 0, bytesRead));
        return list.getUpdateList();
    }
}
