export class StorageReportPayload {
    id!: number;
    name!: String;
    inStock!: number;
    unitSold!: number;
    exprideDate!: String;
    totalPrice!: number;

    constructor(id: number, name: String, inStock: number, unitSold: number, exprideDate: String, totalPrice: number) {
        this.id = id;
        this.name = name;
        this.inStock = inStock;
        this.unitSold = unitSold;
        this.exprideDate = exprideDate;
        this.totalPrice = totalPrice;

    }
}