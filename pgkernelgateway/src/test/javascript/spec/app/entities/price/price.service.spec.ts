/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PriceService } from 'app/entities/price/price.service';
import { IPrice, Price } from 'app/shared/model/price.model';

describe('Service Tests', () => {
  describe('Price Service', () => {
    let injector: TestBed;
    let service: PriceService;
    let httpMock: HttpTestingController;
    let elemDefault: IPrice;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PriceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Price(0, 'AAAAAAA', 'AAAAAAA', 0, 0, false, 0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            modificationDate: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Price', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            modificationDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            modificationDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Price(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Price', async () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            label: 'BBBBBB',
            amount: 1,
            percent: 1,
            amountInPercent: true,
            amountTransactionMax: 1,
            amountTransactionMin: 1,
            currencyCode: 'BBBBBB',
            serviceCode: 'BBBBBB',
            description: 'BBBBBB',
            modificationDate: currentDate.format(DATE_TIME_FORMAT),
            active: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            modificationDate: currentDate
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

      it('should return a list of Price', async () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            label: 'BBBBBB',
            amount: 1,
            percent: 1,
            amountInPercent: true,
            amountTransactionMax: 1,
            amountTransactionMin: 1,
            currencyCode: 'BBBBBB',
            serviceCode: 'BBBBBB',
            description: 'BBBBBB',
            modificationDate: currentDate.format(DATE_TIME_FORMAT),
            active: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            modificationDate: currentDate
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

      it('should delete a Price', async () => {
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
