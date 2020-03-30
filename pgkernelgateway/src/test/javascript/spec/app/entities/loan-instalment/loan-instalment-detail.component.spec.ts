/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { LoanInstalmentDetailComponent } from 'app/entities/loan-instalment/loan-instalment-detail.component';
import { LoanInstalment } from 'app/shared/model/loan-instalment.model';

describe('Component Tests', () => {
  describe('LoanInstalment Management Detail Component', () => {
    let comp: LoanInstalmentDetailComponent;
    let fixture: ComponentFixture<LoanInstalmentDetailComponent>;
    const route = ({ data: of({ loanInstalment: new LoanInstalment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [LoanInstalmentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LoanInstalmentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LoanInstalmentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.loanInstalment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
