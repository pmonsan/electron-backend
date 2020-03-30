/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PersonTypeDetailComponent } from 'app/entities/person-type/person-type-detail.component';
import { PersonType } from 'app/shared/model/person-type.model';

describe('Component Tests', () => {
  describe('PersonType Management Detail Component', () => {
    let comp: PersonTypeDetailComponent;
    let fixture: ComponentFixture<PersonTypeDetailComponent>;
    const route = ({ data: of({ personType: new PersonType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PersonTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PersonTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.personType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
