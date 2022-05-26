export class WardPayload {
    id!: number;
    districtId!: number;
    name!: string;
    type!: string;

    constructor() {
        this.districtId = 1;
    }
}