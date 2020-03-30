/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PriceCommissionService } from 'app/entities/price-commission/price-commission.service';
import { IPriceCommission, PriceCommission } from 'app/shared/model/price-commission.model';

describe('Service Tests', () => {
  describe('PriceCommission Service', () => {
    let injector: TestBed;
    let service: PriceCommissionService;
    let httpMock: HttpTestingController;
    let elemDefault: IPriceCommission;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PriceCommissionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PriceCommission(
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        false,
        currentDate,
        0,
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
            dateCreation: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a PriceCommission', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateCreation: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateCreation: currentDate
          },
          returnedFromService
        );
        service
          .create(new PriceCommission(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PriceCommission', async () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            label: 'BBBBBB',
            amount: 1,
            amountInPercent: true,
            dateCreation: currentDate.format(DATE_TIME_FORMAT),
            percent: 1,
            currencyCode: 'BBBBBB',
            partnerCode: 'BBBBBB',
            serviceCode: 'BBBBBB',
            description: 'BBBBBB',
            active: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreation: currentDate
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

      it('should return a list of PriceCommission', async () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            label: 'BBBBBB',
            amount: 1,
            amountInPercent: true,
            dateCreation: currentDate.format(DATE_TIME_FORMAT),
            percent: 1,
            currencyCode: 'BBBBBB',
            partnerCode: 'BBBBBB',
            serviceCode: 'BBBBBB',
            description: 'BBBBBB',
            active: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateCreation: currentDate
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

      it('should delete a PriceCommission', async () => {
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
