/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CustomerSubscriptionService } from 'app/entities/customer-subscription/customer-subscription.service';
import { ICustomerSubscription, CustomerSubscription } from 'app/shared/model/customer-subscription.model';

describe('Service Tests', () => {
  describe('CustomerSubscription Service', () => {
    let injector: TestBed;
    let service: CustomerSubscriptionService;
    let httpMock: HttpTestingController;
    let elemDefault: ICustomerSubscription;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CustomerSubscriptionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CustomerSubscription(
        0,
        'AAAAAAA',
        currentDate,
        false,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            modificationDate: currentDate.format(DATE_TIME_FORMAT),
            validationDate: currentDate.format(DATE_TIME_FORMAT),
            startDate: currentDate.format(DATE_TIME_FORMAT),
            endDate: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a CustomerSubscription', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            modificationDate: currentDate.format(DATE_TIME_FORMAT),
            validationDate: currentDate.format(DATE_TIME_FORMAT),
            startDate: currentDate.format(DATE_TIME_FORMAT),
            endDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            creationDate: currentDate,
            modificationDate: currentDate,
            validationDate: currentDate,
            startDate: currentDate,
            endDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new CustomerSubscription(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a CustomerSubscription', async () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            isMerchantSubscription: true,
            modificationDate: currentDate.format(DATE_TIME_FORMAT),
            validationDate: currentDate.format(DATE_TIME_FORMAT),
            filename: 'BBBBBB',
            customerCode: 'BBBBBB',
            serviceCode: 'BBBBBB',
            accountNumber: 'BBBBBB',
            startDate: currentDate.format(DATE_TIME_FORMAT),
            endDate: currentDate.format(DATE_TIME_FORMAT),
            active: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDate: currentDate,
            modificationDate: currentDate,
            validationDate: currentDate,
            startDate: currentDate,
            endDate: currentDate
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

      it('should return a list of CustomerSubscription', async () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            isMerchantSubscription: true,
            modificationDate: currentDate.format(DATE_TIME_FORMAT),
            validationDate: currentDate.format(DATE_TIME_FORMAT),
            filename: 'BBBBBB',
            customerCode: 'BBBBBB',
            serviceCode: 'BBBBBB',
            accountNumber: 'BBBBBB',
            startDate: currentDate.format(DATE_TIME_FORMAT),
            endDate: currentDate.format(DATE_TIME_FORMAT),
            active: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            creationDate: currentDate,
            modificationDate: currentDate,
            validationDate: currentDate,
            startDate: currentDate,
            endDate: currentDate
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

      it('should delete a CustomerSubscription', async () => {
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
