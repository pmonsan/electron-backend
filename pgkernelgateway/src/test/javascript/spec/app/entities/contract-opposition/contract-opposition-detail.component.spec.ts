/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ContractOppositionDetailComponent } from 'app/entities/contract-opposition/contract-opposition-detail.component';
import { ContractOpposition } from 'app/shared/model/contract-opposition.model';

describe('Component Tests', () => {
  describe('ContractOpposition Management Detail Component', () => {
    let comp: ContractOppositionDetailComponent;
    let fixture: ComponentFixture<ContractOppositionDetailComponent>;
    const route = ({ data: of({ contractOpposition: new ContractOpposition(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ContractOppositionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContractOppositionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContractOppositionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contractOpposition).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
