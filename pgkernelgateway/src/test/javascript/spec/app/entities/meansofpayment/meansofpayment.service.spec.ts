/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { MeansofpaymentService } from 'app/entities/meansofpayment/meansofpayment.service';
import { IMeansofpayment, Meansofpayment } from 'app/shared/model/meansofpayment.model';

describe('Service Tests', () => {
  describe('Meansofpayment Service', () => {
    let injector: TestBed;
    let service: MeansofpaymentService;
    let httpMock: HttpTestingController;
    let elemDefault: IMeansofpayment;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(MeansofpaymentService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Meansofpayment(
        0,
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

      it('should create a Meansofpayment', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Meansofpayment(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Meansofpayment', async () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            aliasAccount: 'BBBBBB',
            baccBankCode: 'BBBBBB',
            baccBranchCode: 'BBBBBB',
            baccAccountNumber: 'BBBBBB',
            baccRibKey: 'BBBBBB',
            cardCvv2: 'BBBBBB',
            cardPan: 'BBBBBB',
            cardValidityDate: 'BBBBBB',
            momoAccount: 'BBBBBB',
            meansofpaymentTypeCode: 'BBBBBB',
            issuerCode: 'BBBBBB',
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

      it('should return a list of Meansofpayment', async () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            aliasAccount: 'BBBBBB',
            baccBankCode: 'BBBBBB',
            baccBranchCode: 'BBBBBB',
            baccAccountNumber: 'BBBBBB',
            baccRibKey: 'BBBBBB',
            cardCvv2: 'BBBBBB',
            cardPan: 'BBBBBB',
            cardValidityDate: 'BBBBBB',
            momoAccount: 'BBBBBB',
            meansofpaymentTypeCode: 'BBBBBB',
            issuerCode: 'BBBBBB',
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

      it('should delete a Meansofpayment', async () => {
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
