/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LoanInstalmentService } from 'app/entities/loan-instalment/loan-instalment.service';
import { ILoanInstalment, LoanInstalment } from 'app/shared/model/loan-instalment.model';

describe('Service Tests', () => {
  describe('LoanInstalment Service', () => {
    let injector: TestBed;
    let service: LoanInstalmentService;
    let httpMock: HttpTestingController;
    let elemDefault: ILoanInstalment;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(LoanInstalmentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LoanInstalment(0, 'AAAAAAA', currentDate, currentDate, 0, 0, currentDate, 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            expectedPaymentDate: currentDate.format(DATE_TIME_FORMAT),
            realPaymentDate: currentDate.format(DATE_TIME_FORMAT),
            statusDate: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a LoanInstalment', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            expectedPaymentDate: currentDate.format(DATE_TIME_FORMAT),
            realPaymentDate: currentDate.format(DATE_TIME_FORMAT),
            statusDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            expectedPaymentDate: currentDate,
            realPaymentDate: currentDate,
            statusDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new LoanInstalment(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a LoanInstalment', async () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            expectedPaymentDate: currentDate.format(DATE_TIME_FORMAT),
            realPaymentDate: currentDate.format(DATE_TIME_FORMAT),
            amountToPay: 1,
            penalityFee: 1,
            statusDate: currentDate.format(DATE_TIME_FORMAT),
            loanInstalmentStatusCode: 'BBBBBB',
            active: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            expectedPaymentDate: currentDate,
            realPaymentDate: currentDate,
            statusDate: currentDate
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

      it('should return a list of LoanInstalment', async () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            expectedPaymentDate: currentDate.format(DATE_TIME_FORMAT),
            realPaymentDate: currentDate.format(DATE_TIME_FORMAT),
            amountToPay: 1,
            penalityFee: 1,
            statusDate: currentDate.format(DATE_TIME_FORMAT),
            loanInstalmentStatusCode: 'BBBBBB',
            active: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            expectedPaymentDate: currentDate,
            realPaymentDate: currentDate,
            statusDate: currentDate
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

      it('should delete a LoanInstalment', async () => {
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
