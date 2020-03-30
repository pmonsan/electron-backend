/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { BeneficiaryService } from 'app/entities/beneficiary/beneficiary.service';
import { IBeneficiary, Beneficiary } from 'app/shared/model/beneficiary.model';

describe('Service Tests', () => {
  describe('Beneficiary Service', () => {
    let injector: TestBed;
    let service: BeneficiaryService;
    let httpMock: HttpTestingController;
    let elemDefault: IBeneficiary;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(BeneficiaryService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Beneficiary(
        0,
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Beneficiary', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Beneficiary(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Beneficiary', async () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            isCompany: true,
            firstname: 'BBBBBB',
            name: 'BBBBBB',
            aliasAccount: 'BBBBBB',
            baccBankCode: 'BBBBBB',
            baccBranchCode: 'BBBBBB',
            baccAccountNumber: 'BBBBBB',
            baccRibKey: 'BBBBBB',
            cardCvv2: 'BBBBBB',
            cardPan: 'BBBBBB',
            cardValidityDate: 'BBBBBB',
            isDmAccount: true,
            momoAccountNumber: 'BBBBBB',
            beneficiaryRelationshipCode: 'BBBBBB',
            beneficiaryTypeCode: 'BBBBBB',
            active: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Beneficiary', async () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            isCompany: true,
            firstname: 'BBBBBB',
            name: 'BBBBBB',
            aliasAccount: 'BBBBBB',
            baccBankCode: 'BBBBBB',
            baccBranchCode: 'BBBBBB',
            baccAccountNumber: 'BBBBBB',
            baccRibKey: 'BBBBBB',
            cardCvv2: 'BBBBBB',
            cardPan: 'BBBBBB',
            cardValidityDate: 'BBBBBB',
            isDmAccount: true,
            momoAccountNumber: 'BBBBBB',
            beneficiaryRelationshipCode: 'BBBBBB',
            beneficiaryTypeCode: 'BBBBBB',
            active: true
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Beneficiary', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
