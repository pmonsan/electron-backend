/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { LoanInstalmentStatusComponent } from 'app/entities/loan-instalment-status/loan-instalment-status.component';
import { LoanInstalmentStatusService } from 'app/entities/loan-instalment-status/loan-instalment-status.service';
import { LoanInstalmentStatus } from 'app/shared/model/loan-instalment-status.model';

describe('Component Tests', () => {
  describe('LoanInstalmentStatus Management Component', () => {
    let comp: LoanInstalmentStatusComponent;
    let fixture: ComponentFixture<LoanInstalmentStatusComponent>;
    let service: LoanInstalmentStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [LoanInstalmentStatusComponent],
        providers: []
      })
        .overrideTemplate(LoanInstalmentStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LoanInstalmentStatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LoanInstalmentStatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LoanInstalmentStatus(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.loanInstalmentStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
