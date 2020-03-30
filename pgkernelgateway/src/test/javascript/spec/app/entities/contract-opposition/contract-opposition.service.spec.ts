/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ContractOppositionService } from 'app/entities/contract-opposition/contract-opposition.service';
import { IContractOpposition, ContractOpposition } from 'app/shared/model/contract-opposition.model';

describe('Service Tests', () => {
  describe('ContractOpposition Service', () => {
    let injector: TestBed;
    let service: ContractOppositionService;
    let httpMock: HttpTestingController;
    let elemDefault: IContractOpposition;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ContractOppositionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ContractOpposition(0, 'AAAAAAA', false, currentDate, 'AAAAAAA', 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            oppositionDate: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a ContractOpposition', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            oppositionDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            oppositionDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new ContractOpposition(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ContractOpposition', async () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            isCustomerInitiative: true,
            oppositionDate: currentDate.format(DATE_TIME_FORMAT),
            oppositionReason: 'BBBBBB',
            comment: 'BBBBBB',
            active: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            oppositionDate: currentDate
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

      it('should return a list of ContractOpposition', async () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            isCustomerInitiative: true,
            oppositionDate: currentDate.format(DATE_TIME_FORMAT),
            oppositionReason: 'BBBBBB',
            comment: 'BBBBBB',
            active: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            oppositionDate: currentDate
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

      it('should delete a ContractOpposition', async () => {
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
