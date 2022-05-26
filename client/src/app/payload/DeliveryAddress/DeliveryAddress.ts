import { WardPayload } from './WardPayload';
import { DistrictPayload } from './DistrictPayload';
export interface DeliveryAddress{
    id:number;
    userId:number;
    address:string;
    wardId:number;
    districtId:number;
    provinceId:number;
    districts:DistrictPayload[];
    wards:WardPayload[];
}