/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { InternalConnectorRequestService } from 'app/entities/internal-connector-request/internal-connector-request.service';
import { IInternalConnectorRequest, InternalConnectorRequest } from 'app/shared/model/internal-connector-request.model';

describe('Service Tests', () => {
  describe('InternalConnectorRequest Service', () => {
    let injector: TestBed;
    let service: InternalConnectorRequestService;
    let httpMock: HttpTestingController;
    let elemDefault: IInternalConnectorRequest;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(InternalConnectorRequestService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new InternalConnectorRequest(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        false,
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
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
            registrationDate: currentDate.format(DATE_TIME_FORMAT),
            responseDate: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a InternalConnectorRequest', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            registrationDate: currentDate.format(DATE_TIME_FORMAT),
            responseDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            registrationDate: currentDate,
            responseDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new InternalConnectorRequest(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a InternalConnectorRequest', async () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            module: 'BBBBBB',
            connector: 'BBBBBB',
            connectorType: 'BBBBBB',
            requestData: 'BBBBBB',
            registrationDate: currentDate.format(DATE_TIME_FORMAT),
            pgapsTransactionNumber: 'BBBBBB',
            serviceId: 'BBBBBB',
            accountNumber: 'BBBBBB',
            amount: 1,
            balance: 1,
            accountValidation: true,
            numberOfTransactions: 1,
            lastTransactions: 'BBBBBB',
            action: 'BBBBBB',
            responseDate: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            reason: 'BBBBBB',
            partnerTransactionNumber: 'BBBBBB',
            active: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            registrationDate: currentDate,
            responseDate: currentDate
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

      it('should return a list of InternalConnectorRequest', async () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            module: 'BBBBBB',
            connector: 'BBBBBB',
            connectorType: 'BBBBBB',
            requestData: 'BBBBBB',
            registrationDate: currentDate.format(DATE_TIME_FORMAT),
            pgapsTransactionNumber: 'BBBBBB',
            serviceId: 'BBBBBB',
            accountNumber: 'BBBBBB',
            amount: 1,
            balance: 1,
            accountValidation: true,
            numberOfTransactions: 1,
            lastTransactions: 'BBBBBB',
            action: 'BBBBBB',
            responseDate: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            reason: 'BBBBBB',
            partnerTransactionNumber: 'BBBBBB',
            active: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            registrationDate: currentDate,
            responseDate: currentDate
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

      it('should delete a InternalConnectorRequest', async () => {
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
