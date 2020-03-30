/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PgBatchService } from 'app/entities/pg-batch/pg-batch.service';
import { IPgBatch, PgBatch } from 'app/shared/model/pg-batch.model';

describe('Service Tests', () => {
  describe('PgBatch Service', () => {
    let injector: TestBed;
    let service: PgBatchService;
    let httpMock: HttpTestingController;
    let elemDefault: IPgBatch;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PgBatchService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PgBatch(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            expectedEndDate: currentDate.format(DATE_TIME_FORMAT),
            batchDate: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a PgBatch', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            expectedEndDate: currentDate.format(DATE_TIME_FORMAT),
            batchDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            expectedEndDate: currentDate,
            batchDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new PgBatch(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PgBatch', async () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            label: 'BBBBBB',
            expectedEndDate: currentDate.format(DATE_TIME_FORMAT),
            batchDate: currentDate.format(DATE_TIME_FORMAT),
            active: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            expectedEndDate: currentDate,
            batchDate: currentDate
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

      it('should return a list of PgBatch', async () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            label: 'BBBBBB',
            expectedEndDate: currentDate.format(DATE_TIME_FORMAT),
            batchDate: currentDate.format(DATE_TIME_FORMAT),
            active: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            expectedEndDate: currentDate,
            batchDate: currentDate
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

      it('should delete a PgBatch', async () => {
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
