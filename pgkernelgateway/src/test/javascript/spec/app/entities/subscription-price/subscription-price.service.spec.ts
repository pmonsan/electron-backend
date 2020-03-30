/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SubscriptionPriceService } from 'app/entities/subscription-price/subscription-price.service';
import { ISubscriptionPrice, SubscriptionPrice } from 'app/shared/model/subscription-price.model';

describe('Service Tests', () => {
  describe('SubscriptionPrice Service', () => {
    let injector: TestBed;
    let service: SubscriptionPriceService;
    let httpMock: HttpTestingController;
    let elemDefault: ISubscriptionPrice;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(SubscriptionPriceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SubscriptionPrice(0, 0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false);
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

      it('should create a SubscriptionPrice', async () => {
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
          .create(new SubscriptionPrice(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a SubscriptionPrice', async () => {
        const returnedFromService = Object.assign(
          {
            amount: 1,
            description: 'BBBBBB',
            label: 'BBBBBB',
            modificationDate: currentDate.format(DATE_TIME_FORMAT),
            serviceCode: 'BBBBBB',
            accountTypeCode: 'BBBBBB',
            currencyCode: 'BBBBBB',
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

      it('should return a list of SubscriptionPrice', async () => {
        const returnedFromService = Object.assign(
          {
            amount: 1,
            description: 'BBBBBB',
            label: 'BBBBBB',
            modificationDate: currentDate.format(DATE_TIME_FORMAT),
            serviceCode: 'BBBBBB',
            accountTypeCode: 'BBBBBB',
            currencyCode: 'BBBBBB',
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

      it('should delete a SubscriptionPrice', async () => {
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
