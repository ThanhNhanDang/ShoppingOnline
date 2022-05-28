import { Injectable } from '@angular/core';
import { Workbook } from 'exceljs';
import * as fs from "file-saver";

const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;chartset=UTF-8';
const EXCEL_EXTENSION = '.xlsx';
@Injectable({
  providedIn: 'root'
})
export class ExcelService {

  constructor() { }
  public reportAsExcelFile(
    reportHeading: string,
    reportSubHeading: string,
    headersArray: any[],
    json: any[],
    footerData: any,
    excelFileName: string,
    sheetName: string,
  ) {
    const header = headersArray;
    const data = json;

    const workbook = new Workbook();
    workbook.creator = "Admin EStore";
    workbook.lastModifiedBy = "AdminEStore";
    workbook.created = new Date();
    workbook.modified = new Date();
    const worksheet = workbook.addWorksheet(sheetName);

    worksheet.addRow([]);
    worksheet.mergeCells("A1:" + this.numToAlpha(header.length - 1) + '1');
    worksheet.getCell("A1").value = reportHeading;
    worksheet.getCell("A1").alignment = { horizontal: "center" };
    worksheet.getCell("A1").font = { size: 15, bold: true };

    if (reportHeading !== '') {
      worksheet.addRow([]);
      worksheet.mergeCells("A2:" + this.numToAlpha(header.length - 1) + '2');
      worksheet.getCell("A2").value = reportHeading;
      worksheet.getCell("A2").alignment = { horizontal: "center" };
      worksheet.getCell("A2").font = { size: 12, bold: false };
    }

    worksheet.addRow([]);

    const headerRow = worksheet.addRow(header);

    headerRow.eachCell((cell, index) => {
      cell.fill = {
        type: "pattern",
        pattern: "solid",
        fgColor: { argb: "FFFFFF00" },
        bgColor: { argb: "FF0000FF" }
      };
      cell.border = { top: { style: "thin" }, left: { style: "thin" }, bottom: { style: "thin" }, right: { style: "thin" } };
      cell.font = { size: 12, bold: true };
      worksheet.getColumn(index).width = header[index - 1].length < 20 ? 20 : header[index - 1].length;
    })

    let comlumnsArray: any[];
    for (const key in json) {
      if (json.hasOwnProperty(key)) {
        comlumnsArray = Object.keys(json[key]);
      }
    }

    data.forEach((element: any) => {

      const eachRow: any[] = [];
      comlumnsArray.forEach((column) => {
        eachRow.push(element[column]);
      })

      if (element.isDeleted === 'Y') {
        const deleteRow = worksheet.addRow(eachRow);
        deleteRow.eachCell((cell) => {
          cell.font = { name: "Calibri", family: 4, size: 11, bold: false, strike: true };
        })
      } else {
        worksheet.addRow(eachRow);
      }
    })

    worksheet.addRow([]);

    if (footerData !== null) {
      footerData.forEach((element: any) => {

        const eachRow: any[] = [];
        element.forEach((val: any) => {
          eachRow.push(val);
        });

        const footerRow = worksheet.addRow(eachRow);
        footerRow.eachCell((cell) => {
          cell.font = { bold: true };
        })
      });
    }

    workbook.xlsx.writeBuffer().then((data: ArrayBuffer) => {
      const blod = new Blob([data], { type: EXCEL_TYPE });
      fs.saveAs(blod, excelFileName + EXCEL_EXTENSION);
    })

  }

  private numToAlpha(num: number) {
    let alpha = '';
    for (; num >= 0; num = parseInt((num / 26).toString(), 10) - 1) {
      alpha = String.fromCharCode(num % 26 + 0x41) + alpha;
    }
    return alpha;
  }

  public readExcelProvinces(event: any) {
    const json: any[] = [];
    const workbook = new Workbook();
    const target: DataTransfer = <DataTransfer>(event.target);
    if (target.files.length !== 1) {
      throw new Error('Cannot use multiple files');
    }
    const arryBuffer = new Response(target.files[0]).arrayBuffer();
    arryBuffer.then(function (data) {
      workbook.xlsx.load(data)
        .then(function () {
          const worksheet = workbook.getWorksheet(1);
          worksheet.eachRow(function (row, rowNumber) {
            const JSON_ROW = row.values.toString().split(",");
            if (rowNumber > 4) {
              json.push({ "id": JSON_ROW[1], "name": JSON_ROW[2], "type": JSON_ROW[3] })
            }
          });
        });
    });
    return json;
  }
  public readExcelDistricts(event: any) {
    const json: any[] = [];
    const workbook = new Workbook();
    const target: DataTransfer = <DataTransfer>(event.target);
    if (target.files.length !== 1) {
      throw new Error('Cannot use multiple files');
    }
    const arryBuffer = new Response(target.files[0]).arrayBuffer();
    arryBuffer.then(function (data) {
      workbook.xlsx.load(data)
        .then(function () {
          const worksheet = workbook.getWorksheet(1);
          worksheet.eachRow(function (row, rowNumber) {
            const JSON_ROW = row.values.toString().split(",");
            if (rowNumber > 4) {
              json.push({ "id": JSON_ROW[1], "name": JSON_ROW[2], "type": JSON_ROW[3], "provinceId": JSON_ROW[4] })
            }
          });
        });
    });
    return json;
  }

  public readExcelWards(event: any) {
    const json: any[] = [];
    const workbook = new Workbook();
    const target: DataTransfer = <DataTransfer>(event.target);
    if (target.files.length !== 1) {
      throw new Error('Cannot use multiple files');
    }
    const arryBuffer = new Response(target.files[0]).arrayBuffer();
    arryBuffer.then(function (data) {
      workbook.xlsx.load(data)
        .then(function () {
          const worksheet = workbook.getWorksheet(1);
          worksheet.eachRow(function (row, rowNumber) {
            const JSON_ROW = row.values.toString().split(",");
            if (rowNumber > 4) {
              json.push({ "id": JSON_ROW[1], "name": JSON_ROW[2], "type": JSON_ROW[3], "districtId": JSON_ROW[4] })
            }
          });
        });
    });
    return json;
  }
}
