/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { Pg8583RequestService } from 'app/entities/pg-8583-request/pg-8583-request.service';
import { IPg8583Request, Pg8583Request } from 'app/shared/model/pg-8583-request.model';

describe('Service Tests', () => {
  describe('Pg8583Request Service', () => {
    let injector: TestBed;
    let service: Pg8583RequestService;
    let httpMock: HttpTestingController;
    let elemDefault: IPg8583Request;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(Pg8583RequestService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Pg8583Request(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
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

      it('should create a Pg8583Request', async () => {
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
          .create(new Pg8583Request(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Pg8583Request', async () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            apiKey: 'BBBBBB',
            encryptedData: 'BBBBBB',
            decryptedData: 'BBBBBB',
            registrationDate: currentDate.format(DATE_TIME_FORMAT),
            responseDate: currentDate.format(DATE_TIME_FORMAT),
            requestResponse: 'BBBBBB',
            status: 'BBBBBB',
            reason: 'BBBBBB',
            pgapsMessage: 'BBBBBB',
            pgapsTransactionNumber: 'BBBBBB',
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

      it('should return a list of Pg8583Request', async () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            apiKey: 'BBBBBB',
            encryptedData: 'BBBBBB',
            decryptedData: 'BBBBBB',
            registrationDate: currentDate.format(DATE_TIME_FORMAT),
            responseDate: currentDate.format(DATE_TIME_FORMAT),
            requestResponse: 'BBBBBB',
            status: 'BBBBBB',
            reason: 'BBBBBB',
            pgapsMessage: 'BBBBBB',
            pgapsTransactionNumber: 'BBBBBB',
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

      it('should delete a Pg8583Request', async () => {
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
