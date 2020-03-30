/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { PgServiceService } from 'app/entities/pg-service/pg-service.service';
import { IPgService, PgService } from 'app/shared/model/pg-service.model';

describe('Service Tests', () => {
  describe('PgService Service', () => {
    let injector: TestBed;
    let service: PgServiceService;
    let httpMock: HttpTestingController;
    let elemDefault: IPgService;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PgServiceService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PgService(
        0,
        'AAAAAAA',
        'AAAAAAA',
        false,
        false,
        false,
        false,
        'AAAAAAA',
        false,
        false,
        false,
        false,
        false,
        false,
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

      it('should create a PgService', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new PgService(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PgService', async () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            name: 'BBBBBB',
            isNative: true,
            isSourceInternal: true,
            isDestinationInternal: true,
            needSubscription: true,
            currencyCode: 'BBBBBB',
            useTransactionType: true,
            checkSubscription: true,
            ignoreFees: true,
            ignoreLimit: true,
            ignoreCommission: true,
            checkOtp: true,
            pgTransactionType1Code: 'BBBBBB',
            pgTransactionType2Code: 'BBBBBB',
            partnerOwnerCode: 'BBBBBB',
            transactionTypeCode: 'BBBBBB',
            serviceAuthenticationCode: 'BBBBBB',
            contractPath: 'BBBBBB',
            description: 'BBBBBB',
            logic: 'BBBBBB',
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

      it('should return a list of PgService', async () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            name: 'BBBBBB',
            isNative: true,
            isSourceInternal: true,
            isDestinationInternal: true,
            needSubscription: true,
            currencyCode: 'BBBBBB',
            useTransactionType: true,
            checkSubscription: true,
            ignoreFees: true,
            ignoreLimit: true,
            ignoreCommission: true,
            checkOtp: true,
            pgTransactionType1Code: 'BBBBBB',
            pgTransactionType2Code: 'BBBBBB',
            partnerOwnerCode: 'BBBBBB',
            transactionTypeCode: 'BBBBBB',
            serviceAuthenticationCode: 'BBBBBB',
            contractPath: 'BBBBBB',
            description: 'BBBBBB',
            logic: 'BBBBBB',
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

      it('should delete a PgService', async () => {
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
