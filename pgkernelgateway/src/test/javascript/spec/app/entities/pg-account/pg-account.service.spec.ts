/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PgAccountService } from 'app/entities/pg-account/pg-account.service';
import { IPgAccount, PgAccount } from 'app/shared/model/pg-account.model';

describe('Service Tests', () => {
  describe('PgAccount Service', () => {
    let injector: TestBed;
    let service: PgAccountService;
    let httpMock: HttpTestingController;
    let elemDefault: IPgAccount;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PgAccountService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PgAccount(
        0,
        'AAAAAAA',
        currentDate,
        false,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
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
        const returnedFromService = Object.assign(
          {
            openingDate: currentDate.format(DATE_TIME_FORMAT),
            closingDate: currentDate.format(DATE_TIME_FORMAT),
            validationDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a PgAccount', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            openingDate: currentDate.format(DATE_TIME_FORMAT),
            closingDate: currentDate.format(DATE_TIME_FORMAT),
            validationDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            openingDate: currentDate,
            closingDate: currentDate,
            validationDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new PgAccount(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PgAccount', async () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            openingDate: currentDate.format(DATE_TIME_FORMAT),
            temporary: true,
            closingDate: currentDate.format(DATE_TIME_FORMAT),
            imsi: 'BBBBBB',
            transactionCode: 'BBBBBB',
            validationDate: currentDate.format(DATE_TIME_FORMAT),
            accountStatusCode: 'BBBBBB',
            accountTypeCode: 'BBBBBB',
            customerCode: 'BBBBBB',
            currencyCode: 'BBBBBB',
            comment: 'BBBBBB',
            active: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            openingDate: currentDate,
            closingDate: currentDate,
            validationDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of PgAccount', async () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            openingDate: currentDate.format(DATE_TIME_FORMAT),
            temporary: true,
            closingDate: currentDate.format(DATE_TIME_FORMAT),
            imsi: 'BBBBBB',
            transactionCode: 'BBBBBB',
            validationDate: currentDate.format(DATE_TIME_FORMAT),
            accountStatusCode: 'BBBBBB',
            accountTypeCode: 'BBBBBB',
            customerCode: 'BBBBBB',
            currencyCode: 'BBBBBB',
            comment: 'BBBBBB',
            active: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            openingDate: currentDate,
            closingDate: currentDate,
            validationDate: currentDate
          },
          returnedFromService
        );
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

      it('should delete a PgAccount', async () => {
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
