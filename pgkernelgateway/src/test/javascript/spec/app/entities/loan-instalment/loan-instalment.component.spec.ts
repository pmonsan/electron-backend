/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { LoanInstalmentComponent } from 'app/entities/loan-instalment/loan-instalment.component';
import { LoanInstalmentService } from 'app/entities/loan-instalment/loan-instalment.service';
import { LoanInstalment } from 'app/shared/model/loan-instalment.model';

describe('Component Tests', () => {
  describe('LoanInstalment Management Component', () => {
    let comp: LoanInstalmentComponent;
    let fixture: ComponentFixture<LoanInstalmentComponent>;
    let service: LoanInstalmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [LoanInstalmentComponent],
        providers: []
      })
        .overrideTemplate(LoanInstalmentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LoanInstalmentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LoanInstalmentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LoanInstalment(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.loanInstalments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
